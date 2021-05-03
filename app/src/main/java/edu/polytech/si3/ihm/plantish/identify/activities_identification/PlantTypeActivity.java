package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Intent;
import android.os.Bundle;

import edu.polytech.si3.ihm.plantish.identify.lists_identification.ListType;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria.TYPE;

public class PlantTypeActivity extends CriterionActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void setCriterionList() {
        criterionList = new ListType();
    }

    @Override
    protected void setPlantCriteria() {
        plantCriteria = TYPE;
    }

    @Override
    protected void setIntentToNextActivity(){
        intentToNextActivity = new Intent(getApplicationContext(),PlantColorActivity.class);
    }



}

