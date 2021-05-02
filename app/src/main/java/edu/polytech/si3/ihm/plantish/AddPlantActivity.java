package edu.polytech.si3.ihm.plantish;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.MotionEventCompat;
import androidx.preference.PreferenceManager;

import com.google.android.gms.maps.model.LatLng;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.polytech.si3.ihm.plantish.plants.BushFactory;
import edu.polytech.si3.ihm.plantish.plants.FlowerFactory;
import edu.polytech.si3.ihm.plantish.plants.Plant;
import edu.polytech.si3.ihm.plantish.plants.PlantFactory;
import edu.polytech.si3.ihm.plantish.plants.TreeFactory;
import edu.polytech.si3.ihm.plantish.posts.Post;
import edu.polytech.si3.ihm.plantish.user.Session;
import edu.polytech.si3.ihm.plantish.user.User;

/**
 * @author Marine Demonchaux
 */

public class AddPlantActivity extends AppCompatActivity {

    EditText date;
    EditText description;
    Spinner spinnerType;
    Spinner spinnerFamily;
    Bitmap bitmap;
    User user;

    DatePickerDialog datePickerDialog;
    ImageButton imageButton;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int GALLERY_REQUEST = 2;
    private GeoPoint position;
    MapView map;
    protected static final int DEFAULT_INACTIVITY_DELAY_IN_MILLISECS = 1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_plant);
        org.osmdroid.config.Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        this.user = Session.getInstance().getUser();

        //DATE
        // initiate the date picker and a button
        date = (EditText) findViewById(R.id.date);
        // perform click event on edit text
        setCalendar();

        //** Description
        this.description = (EditText) findViewById(R.id.editText2);

        //** Spinner
        this.spinnerType = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_plant, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerType.setAdapter(adapter);



        this.spinnerFamily = (Spinner) findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
               getResource(), android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFamily.setAdapter(adapter2);


        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(AddPlantActivity.this,
                        getResource(), android.R.layout.simple_spinner_item);
                // Specify the layout to use when the list of choices appears
                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFamily.setAdapter(adapter2);

             //   ImageView imageView = (ImageView) findViewById(R.id.imageView);
             //   imageView.setImageBitmap();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //**

        // Apply the adapter to the spinner
        spinnerFamily.setAdapter(adapter2);
        //** Camera
        this.imageView = (ImageView) findViewById(R.id.imageView);
        cameraButton();
        galleryButton();
        //**

        //**  Location
        position = MyLocationListener.getLocation(this);
        //**

        //** Address
       this.setLocationOnEditText();
        //**

        //** Map
        if( position != null)
            initializeMap(position);

        // Add button
        Button addButton = (Button) findViewById(R.id.button);
        addPlant(addButton);

    }

    private int getResource(){
        String typeSelected = this.spinnerType.getSelectedItem().toString();
        if(englishToFrench(Plant.BUSH).equals(typeSelected))
            return R.array.familybush;
        else if(englishToFrench(Plant.TREE).equals(typeSelected))
            return R.array.familytree;
        else return R.array.familyflower;

    }

    private int getPositionStringInArrayRes(int id, String string){
        List<String> list = Arrays.asList(getResources().getStringArray(id));
        string = englishToFrench(string);
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).equalsIgnoreCase(string)) return i;
        }
        return 0;
    }

    private String englishToFrench(String s){
        if(s.equals("FLOWER")) return "Fleur";
        if(s.equals("BUSH")) return "Buisson";
        if(s.equals("TREE")) return "Arbre";
        else return s;
    }

    private void setLocationOnEditText(){
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(position.getLatitude(), position.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        EditText editAddress = (EditText) findViewById(R.id.editTextAdress);
        editAddress.setText(address);

        editAddress.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String string = editAddress.getText().toString();
                LatLng latlngLocation = AddPlantActivity.this.getLocationFromAddress(AddPlantActivity.this, string);
                position.setLatitude(latlngLocation.latitude);
                position.setLongitude(latlngLocation.longitude);
                initializeMap(position);
                return true;

            }
        });
    }
    public LatLng getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    private boolean addPlant(Button addButton){

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date datePost = new Date();
                String dateString = AddPlantActivity.this.date.getText().toString();

                datePost.setDate(Integer.parseInt(dateString.split("/")[0]));
                datePost.setYear(Integer.parseInt(dateString.split("/")[2]));
                datePost.setMonth(Integer.parseInt(dateString.split("/")[1]));

                String description = AddPlantActivity.this.description.getText().toString();
                String type = AddPlantActivity.this.spinnerType.getSelectedItem().toString();
                String family = AddPlantActivity.this.spinnerFamily.getSelectedItem().toString();

                PlantFactory plantFactory;
                if (type.equals(PlantFactory.BUSH)) {
                    plantFactory = new BushFactory();
                } else if (type.equals(PlantFactory.FLOWER)) {
                    plantFactory = new FlowerFactory();
                } else {
                    plantFactory = new TreeFactory();
                }
                Plant plant = plantFactory.build(position, family, description);
                Post post;
                if (bitmap == null)
                    post = plantFactory.build(user, datePost, plant);
                else
                    post = plantFactory.build(user, datePost, bitmap, plant);

                Intent intent = new Intent(AddPlantActivity.this, MyPlantsActivity.class);
                startActivity(intent);

            }
        });
        return false;

    }

    private void setCalendar(){
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddPlantActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear + 1, year));
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }


    private void galleryButton(){
        imageButton = (ImageButton) findViewById(R.id.galleryBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }});
    }



    private void cameraButton(){
        imageButton = (ImageButton) findViewById(R.id.cameraBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }});
    }

    private void initializeMap(GeoPoint position){
        map = findViewById(R.id.mapObject1);
        map.getOverlays().clear();
        map.setTileSource(TileSourceFactory.MAPNIK); //Render
        map.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(position.getLatitude(), position.getLongitude()); //Données de Marine ici
        IMapController controller = map.getController();
        controller.setZoom(19.2); //Valeur par défault du zoom
        controller.setCenter(startPoint); //Centrer la carte sur ce point

        setOverlayItem(map, startPoint);
        Log.d("DRAG", startPoint+"");

        map.setMapListener(new DelayedMapListener(new MapListener() {
            @Override
            public boolean onScroll(ScrollEvent event) {
                position.setLatitude(map.getMapCenter().getLatitude());
                position.setLongitude(map.getMapCenter().getLongitude());
                map.getOverlays().clear();
                setOverlayItem(map, new GeoPoint(position.getLatitude(), position.getLongitude()));
                setLocationOnEditText();
                return false;
            }

            @Override
            public boolean onZoom(ZoomEvent event) {
                position.setLatitude(map.getMapCenter().getLatitude());
                position.setLongitude(map.getMapCenter().getLongitude());
                map.getOverlays().clear();
                setOverlayItem(map, new GeoPoint(position.getLatitude(), position.getLongitude()));
                setLocationOnEditText();
                return false;
            }
        }, DEFAULT_INACTIVITY_DELAY_IN_MILLISECS));


    }

    private void setOverlayItem(MapView map, GeoPoint startPoint){
        List<OverlayItem> marqueurs = new ArrayList<>();
        OverlayItem home = new OverlayItem("Votre Plante", "", startPoint);
        Bitmap d =  BitmapFactory.decodeResource(MainActivity.context.getResources(), R.mipmap.grass); //Changer l'image du marqueur, ici un qui existe déjà
        BitmapDrawable dd = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(d, 80, 80, true));
        home.setMarker(dd);
        marqueurs.add(home);

        ItemizedOverlayWithFocus<OverlayItem> itemizedOverlayWithFocus = new ItemizedOverlayWithFocus<>(getApplicationContext(), marqueurs, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true; // Pour qu'il réagisse quand j'ai cliqué dessus
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });
        itemizedOverlayWithFocus.setFocusItemsOnTap(true);
        map.getOverlays().add(itemizedOverlayWithFocus);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            if (requestCode == GALLERY_REQUEST) {
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Log.i("TAG", "Some exception " + e);
                }
            }
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }




}