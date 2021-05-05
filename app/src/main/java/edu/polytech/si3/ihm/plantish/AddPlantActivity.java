package edu.polytech.si3.ihm.plantish;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.text.ParseException;
import java.util.Date;

import edu.polytech.si3.ihm.plantish.find.FindPlantFilterActivity;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_plant, container, false);
        //Exemple d'instanciation d'éléments graphiques. Ils devraient être ceux de votre activité.

        return view;
    }


    private Context ctx ;
    public void onActivityCreated(Bundle savedInstanceState) {
        ctx = getActivity();
        v = getView();

        super.onActivityCreated(savedInstanceState);

        super.onCreate(savedInstanceState);
        org.osmdroid.config.Configuration.getInstance().load(ctx.getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(ctx.getApplicationContext()));

        this.user = Session.getInstance().getUser();

        //DATE
        // initiate the date picker and a button
        date = (DatePicker) getView().findViewById(R.id.date);
        // perform click event on edit text
        setCalendar();

        //** Description
        this.description = (EditText)  v.findViewById(R.id.editText2);

        //** Spinner
        this.spinnerType = (Spinner) v.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx,
                R.array.type_plant, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerType.setAdapter(adapter);



        this.spinnerFamily = (Spinner) v.findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(ctx,
               getResource(), android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFamily.setAdapter(adapter2);


        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(ctx,
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
        this.imageView = (ImageView) v.findViewById(R.id.imageView);
        cameraButton();
        galleryButton();
        //**

        //**  Location
        position = MyLocationListener.getLocation(this.getActivity());
        //**

        //** Address
       this.setLocationOnEditText();
        //**

        //** Map
        if( position != null)
            initializeMap(position);

        // Add button
        Button addButton = (Button) v.findViewById(R.id.button);
        addPlant(addButton);

    }



    private boolean addPlant(Button addButton){

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date datePost = new Date();
                DatePicker datePicker = AddPlantActivity.this.date;
                String dateString = datePicker.getDayOfMonth()+"/"+(datePicker.getMonth()+1)+"/"+(datePicker.getYear());

                try {
                    datePost = sdf.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

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

                MyPlantsActivity myPlantsActivity = new MyPlantsActivity();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, myPlantsActivity);
                fragmentTransaction.commit();

            }
        });
        return false;

    }





}