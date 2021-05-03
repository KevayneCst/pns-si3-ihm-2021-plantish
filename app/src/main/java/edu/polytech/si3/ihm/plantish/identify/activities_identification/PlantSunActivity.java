package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Intent;
import android.os.Bundle;

import edu.polytech.si3.ihm.plantish.identify.lists_identification.ListSun;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria.SUN;

public class PlantSunActivity extends CriterionActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setCriterionList() {
        criterionList = new ListSun();
    }

    @Override
    protected void setPlantCriteria() {
        plantCriteria = SUN;
    }

    @Override
    protected void setIntentToNextActivity(){
        intentToNextActivity = new Intent(getApplicationContext(),LeafShapeActivity.class);
    }
}