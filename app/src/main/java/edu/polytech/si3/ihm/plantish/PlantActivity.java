package edu.polytech.si3.ihm.plantish;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.model.LatLng;

import org.osmdroid.api.IMapController;
import org.osmdroid.events.DelayedMapListener;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.polytech.si3.ihm.plantish.plants.Plant;
import edu.polytech.si3.ihm.plantish.user.User;

public abstract class PlantActivity extends Fragment {

    DatePicker date;
    EditText description;
    Spinner spinnerType;
    Spinner spinnerFamily;
    Bitmap bitmap;
    User user;
    View v;
    DatePickerDialog datePickerDialog;
    ImageButton imageButton;
    static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    static final int MY_CAMERA_PERMISSION_CODE = 100;
    static final int GALLERY_REQUEST = 2;
    GeoPoint position;
    MapView map;
    protected static final int DEFAULT_INACTIVITY_DELAY_IN_MILLISECS = 1 ;
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");




    int getPositionStringInArrayRes(int id, String string){
        List<String> list = Arrays.asList(getResources().getStringArray(id));
        string = englishToFrench(string);
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).equalsIgnoreCase(string)) return i;
        }
        return 0;
    }

    public static String englishToFrench(String s){
        if(s.equals("FLOWER")) return "Fleur";
        if(s.equals("BUSH")) return "Buisson";
        if(s.equals("TREE")) return "Arbre";
        else return s;
    }

    void setLocationOnEditText(){
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(v.getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(position.getLatitude(), position.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        EditText editAddress = (EditText) v.findViewById(R.id.editTextAdress);
        editAddress.setText(address);

        editAddress.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String string = editAddress.getText().toString();
                if(!string.isEmpty()) {
                    LatLng latlngLocation = PlantActivity.this.getLocationFromAddress(v.getContext(), string);
                    if(latlngLocation != null) {
                        position.setLatitude(latlngLocation.latitude);
                        position.setLongitude(latlngLocation.longitude);
                        initializeMap(position);
                    }
                }
                return true;

            }
        });
    }

    int getResource(){
        String typeSelected = this.spinnerType.getSelectedItem().toString();
        if(englishToFrench(Plant.BUSH).equals(typeSelected))
            return R.array.familybush;
        else if(englishToFrench(Plant.TREE).equals(typeSelected))
            return R.array.familytree;
        else return R.array.familyflower;

    }

    void initializeMap(GeoPoint position){
        map = v.findViewById(R.id.mapObject1);
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

    public static class MapOverlay extends org.osmdroid.views.overlay.Overlay {

        public MapOverlay(Context ctx) {super(ctx);}

        @Override
        public void draw(Canvas c, MapView osmv, boolean shadow) { }

        @Override
        public boolean onTouchEvent(MotionEvent e, MapView mapView) {
            int action = e.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // Disallow ScrollView to intercept touch events.
                    mapView.getParent().requestDisallowInterceptTouchEvent(true);
                    break;

                case MotionEvent.ACTION_UP:
                    // Allow ScrollView to intercept touch events.
                    mapView.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
            }

            // Handle MapView's touch events.
            mapView.onTouchEvent(e);
            return false;
        }
    }

    void setOverlayItem(MapView map, GeoPoint startPoint){
        List<OverlayItem> marqueurs = new ArrayList<>();
        OverlayItem home = new OverlayItem("Votre Plante", "", startPoint);
        Bitmap d =  BitmapFactory.decodeResource(MainActivity.context.getResources(), R.mipmap.grass); //Changer l'image du marqueur, ici un qui existe déjà
        BitmapDrawable dd = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(d, 80, 80, true));
        home.setMarker(dd);
        marqueurs.add(home);

        ItemizedOverlayWithFocus<OverlayItem> itemizedOverlayWithFocus = new ItemizedOverlayWithFocus<>(v.getContext().getApplicationContext(), marqueurs, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true; // Pour qu'il réagisse quand j'ai cliqué dessus
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });

        MapOverlay movl = new MapOverlay(v.getContext());
        map.getOverlays().add(movl);

        itemizedOverlayWithFocus.setFocusItemsOnTap(true);
        map.getOverlays().add(itemizedOverlayWithFocus);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

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

    void galleryButton(){
        imageButton = (ImageButton) v.findViewById(R.id.galleryBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }});
    }



    void cameraButton(){
        imageButton = (ImageButton) v.findViewById(R.id.cameraBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }});
    }


    void setCalendar(){
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                Date dateCalendar = Calendar.getInstance().getTime();

                // date picker dialog
                datePickerDialog = new DatePickerDialog(v.getContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                //String strDate = sdf.format(dateCalendar);
                                //date.setText(strDate);

                            }
                        }, dateCalendar.getYear(), dateCalendar.getMonth(), dateCalendar.getDay());
                datePickerDialog.show();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(v.getContext(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(v.getContext(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            if (requestCode == GALLERY_REQUEST) {
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(v.getContext().getContentResolver(), selectedImage);
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
