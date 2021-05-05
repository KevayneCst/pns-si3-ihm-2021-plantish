package edu.polytech.si3.ihm.plantish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import java.text.ParseException;
import java.util.Date;

import edu.polytech.si3.ihm.plantish.plants.BushFactory;
import edu.polytech.si3.ihm.plantish.plants.FlowerFactory;
import edu.polytech.si3.ihm.plantish.plants.Plant;
import edu.polytech.si3.ihm.plantish.plants.PlantFactory;
import edu.polytech.si3.ihm.plantish.plants.TreeFactory;
import edu.polytech.si3.ihm.plantish.posts.Post;
import edu.polytech.si3.ihm.plantish.user.Session;

/**
 * @author Marine Demonchaux
 */

public class UpdatePlantActivity extends PlantActivity {


    private int pos;
    private Post post;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.pos = getArguments().getInt("Position", 0);
        View view = inflater.inflate(R.layout.update_plant, container, false);
        //Exemple d'instanciation d'éléments graphiques. Ils devraient être ceux de votre activité.

        return view;
    }


    private Context ctx ;
    public void onActivityCreated(Bundle savedInstanceState) {
        ctx = getActivity();
        v = getView();

        super.onActivityCreated(savedInstanceState);

        org.osmdroid.config.Configuration.getInstance().load(ctx.getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(ctx.getApplicationContext()));

        this.user = Session.getInstance().getUser();


        this.post = Session.getUserPost(pos);

        //DATE
        // initiate the date picker and a button
        date = (DatePicker) v.findViewById(R.id.date);
        Date datePost = post.getDate();
        Log.d("DATE", datePost.toString());
        String[] string = sdf.format(datePost).split("/");
        date.updateDate( Integer.parseInt(string[2]), Integer.parseInt(string[1])-1, Integer.parseInt(string[0]));
        // perform click event on edit text



        //** Description
        this.description = (EditText) v.findViewById(R.id.editText2);
        this.description.setText(post.getPlant().getDescription());

        //** Spinner
        this.spinnerType = (Spinner) v.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(ctx,
                R.array.type_plant, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerType.setAdapter(adapter);
        spinnerType.setSelection(this.getPositionStringInArrayRes(R.array.type_plant, post.getPlant().getTYPE()));



        this.spinnerFamily = (Spinner) v.findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(ctx,
                R.array.familybush, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFamily.setAdapter(adapter2);
        spinnerFamily.setSelection(this.getPositionStringInArrayRes(R.array.familybush, post.getPlant().getFamily()));
        //**

        //** Camera
        this.imageView = (ImageView) v.findViewById(R.id.imageView);
        bitmap = post.getImage();
        this.imageView.setImageBitmap(bitmap);
        cameraButton();
        galleryButton();
        //**

        //**  Location
        position = post.getPlant().getPosition();
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
                DatePicker datePicker = UpdatePlantActivity.this.date;

                String dateString = datePicker.getDayOfMonth()+"/"+datePicker.getMonth()+1+"/"+datePicker.getYear();

                try {
                    datePost = sdf.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String description = UpdatePlantActivity.this.description.getText().toString();
                String type = UpdatePlantActivity.this.spinnerType.getSelectedItem().toString();
                String family = UpdatePlantActivity.this.spinnerFamily.getSelectedItem().toString();

                PlantFactory plantFactory;
                if (type.equals(PlantFactory.BUSH)) {
                    plantFactory = new BushFactory();
                } else if (type.equals(PlantFactory.FLOWER)) {
                    plantFactory = new FlowerFactory();
                } else {
                    plantFactory = new TreeFactory();
                }
                Session.deletePostInApp(UpdatePlantActivity.this.post);
                Plant plant = plantFactory.build(position, family, description);
                Post post;
                if (bitmap == null)
                    post = plantFactory.build(user, datePost, plant);
                else
                    post = plantFactory.build(user, datePost, bitmap, plant);

                Session.deletePost(post);
                Session.updatePost(pos, post);

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