package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.R;


public class PlantNotFoundActivity extends AppCompatActivity {

    private Button askCommunityButton;
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_not_found);
        askCommunityButton =  findViewById(R.id.askCommunityButton);
        homeButton = findViewById(R.id.mainButton);
        askCommunityButton.setOnClickListener(click -> onClickAskCommunityButton());
        homeButton.setOnClickListener(click -> onClickHomeButton());
    }

    private void onClickAskCommunityButton(){

    }

    private void onClickHomeButton(){
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, PlantTypeActivity.class));
    }
}