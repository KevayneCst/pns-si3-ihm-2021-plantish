package edu.polytech.si3.ihm.plantish;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.preference.PreferenceManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_plant);
        org.osmdroid.config.Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        this.user = Session.getInstance().getUser();
        this.pos = getIntent().getIntExtra("Position", 0);

        this.post = Session.getUserPost(pos);

        //DATE
        // initiate the date picker and a button
        date = (EditText) findViewById(R.id.date);
        // perform click event on edit text
        setCalendar();
        date.setText(post.getDate().getDay()+"/"+post.getDate().getMonth()+"/"+post.getDate().getYear());

        //** Description
        this.description = (EditText) findViewById(R.id.editText2);
        this.description.setText(post.getPlant().getDescription());

        //** Spinner
        this.spinnerType = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_plant, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerType.setAdapter(adapter);
        spinnerType.setSelection(this.getPositionStringInArrayRes(R.array.type_plant, post.getPlant().getTYPE()));



        this.spinnerFamily = (Spinner) findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.familybush, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerFamily.setAdapter(adapter2);
        spinnerFamily.setSelection(this.getPositionStringInArrayRes(R.array.familybush, post.getPlant().getFamily()));
        //**

        //** Camera
        this.imageView = (ImageView) findViewById(R.id.imageView);
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
        Button addButton = (Button) findViewById(R.id.button);
        addPlant(addButton);

    }


    private boolean addPlant(Button addButton){

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date datePost = new Date();
                String dateString = UpdatePlantActivity.this.date.getText().toString();

                datePost.setDate(Integer.parseInt(dateString.split("/")[0]));
                datePost.setYear(Integer.parseInt(dateString.split("/")[2]));
                datePost.setMonth(Integer.parseInt(dateString.split("/")[1]));

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

                Intent intent = new Intent(UpdatePlantActivity.this, MyPlantsActivity.class);
                startActivity(intent);

            }
        });
        return false;

    }



}