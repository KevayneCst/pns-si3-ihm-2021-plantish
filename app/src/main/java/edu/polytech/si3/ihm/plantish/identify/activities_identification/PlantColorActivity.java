package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Intent;
import android.os.Bundle;

import edu.polytech.si3.ihm.plantish.identify.lists_identification.ListColor;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria.COLOR;

public class PlantColorActivity extends CriterionActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setCriterionList() {
        criterionList = new ListColor();
    }

    @Override
    protected void setPlantCriteria() {
        plantCriteria = COLOR;
    }

    @Override
    protected void setIntentToNextActivity(){
        intentToNextActivity = new Intent(getApplicationContext(),PlantSeasonActivity.class);
    }
}