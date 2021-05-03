package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.identify.DataLoader;
import edu.polytech.si3.ihm.plantish.identify.PlantFound;

import static edu.polytech.si3.ihm.plantish.identify.Application.PLANT_FOUND;

public class PlantFoundActivity extends AppCompatActivity {

    private PlantFound plantFound;
    private TextView label;
    private TextView type;
    private TextView color;
    private TextView season;
    private TextView sun;
    private TextView shape;
    private TextView layout;
    private ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_found);

        label = findViewById(R.id.plantFoundLabel);
        type = findViewById(R.id.plantFoundType);
        color = findViewById(R.id.plantFoundColor);
        season = findViewById(R.id.plantFoundSeason);
        sun = findViewById(R.id.plantFoundSun);
        shape = findViewById(R.id.plantFoundShape);
        layout = findViewById(R.id.plantFoundLayout);
        picture = findViewById(R.id.plantFoundPicture);

        plantFound = getIntent().getParcelableExtra(PLANT_FOUND);
        setActivityWidgets();

    }

    private void setActivityWidgets(){
        label.setText(plantFound.getLabel());
        type.setText(plantFound.getType());
        color.setText(plantFound.getColor());
        season.setText(plantFound.getSeason());
        sun.setText(plantFound.getSun());
        shape.setText(plantFound.getShape());
        layout.setText(plantFound.getLayout());
        DataLoader.setImageViewFromPath(plantFound.getPicture(),picture);


    }

}