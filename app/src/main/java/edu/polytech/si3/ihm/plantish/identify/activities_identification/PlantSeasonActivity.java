package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Intent;
import android.os.Bundle;

import edu.polytech.si3.ihm.plantish.identify.lists_identification.ListSeason;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria.SEASON;

public class PlantSeasonActivity extends CriterionActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setCriterionList() {
        criterionList = new ListSeason();
    }

    @Override
    protected void setPlantCriteria() {
        plantCriteria = SEASON;
    }

    @Override
    protected void setIntentToNextActivity(){
        intentToNextActivity = new Intent(getApplicationContext(),PlantSunActivity.class);
    }
}