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

public class AddPlantActivity extends PlantActivity {




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













}