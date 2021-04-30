package edu.polytech.si3.ihm.plantish;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
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
import androidx.preference.PreferenceManager;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 1;
    private static final int GALLERY_REQUEST = 2;
    private static final int WRITE_REQUEST_CODE = 3;
    private Location location;


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
                R.array.family_plant, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFamily.setAdapter(adapter2);
        //**

        //** Camera
        this.imageView = (ImageView) findViewById(R.id.imageView);
        cameraButton();
        galleryButton();
        //**

        //**  Location
        MyLocationListener.Request_FINE_LOCATION(AddPlantActivity.this, MY_PERMISSION_ACCESS_COURSE_LOCATION);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            assert location != null;
            Log.d("LOCATION", location.toString());
        } catch (SecurityException e) {
            Log.d("GPS","GPS NOT FOUND");
        }
        //**

        //** Map
        if( location != null)
            initializeMap(location);

        // Add button
        Button addButton = (Button) findViewById(R.id.button);
        addPlant(addButton);

    }

    private boolean addPlant(Button addButton){

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, WRITE_REQUEST_CODE);



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

    private void initializeMap(Location location){
        MapView map = findViewById(R.id.mapObject1);
        map.setTileSource(TileSourceFactory.MAPNIK); //Render
        map.setBuiltInZoomControls(true); //Zoomable
        GeoPoint startPoint = new GeoPoint(location.getLatitude(), location.getLongitude()); //Données de Marine ici
        IMapController controller = map.getController();
        controller.setZoom(19.2); //Valeur par défault du zoom
        controller.setCenter(startPoint); //Centrer la carte sur ce point

        List<OverlayItem> marqueurs = new ArrayList<>();
        OverlayItem home = new OverlayItem("Point A", "Il s'agit du point A", startPoint);
        Drawable d = home.getMarker(0); //Changer l'image du marqueur, ici un qui existe déjà
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
        if (requestCode == WRITE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                Plant plant = plantFactory.build(location, family, description);
                Post post;
                if (bitmap == null)
                    post = plantFactory.build(user, datePost, plant);
                else
                    post = plantFactory.build(user, datePost, bitmap, plant);

                Intent intent = new Intent(AddPlantActivity.this, MyPlantsActivity.class);
                startActivity(intent);
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