package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Intent;
import android.os.Bundle;

import edu.polytech.si3.ihm.plantish.identify.lists_identification.ListLayout;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria.LAYOUT;

public class LeafLayoutActivity extends CriterionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setCriterionList() {
        criterionList = new ListLayout();
    }

    @Override
    protected void setPlantCriteria() {
        plantCriteria = LAYOUT;
    }

    @Override
    protected void setIntentToNextActivity(){
        intentToNextActivity = new Intent(getApplicationContext(), ListOfPlantsFoundActivity.class);
    }

}