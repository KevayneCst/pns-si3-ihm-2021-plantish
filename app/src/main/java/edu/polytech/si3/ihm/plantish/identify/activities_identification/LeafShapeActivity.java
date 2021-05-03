package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Intent;
import android.os.Bundle;

import edu.polytech.si3.ihm.plantish.identify.lists_identification.ListShape;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria.SHAPE;

public class LeafShapeActivity extends CriterionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setCriterionList() {
        criterionList = new ListShape();
    }

    @Override
    protected void setPlantCriteria() {
        plantCriteria = SHAPE;
    }

    @Override
    protected void setIntentToNextActivity(){
        intentToNextActivity = new Intent(getApplicationContext(),LeafLayoutActivity.class);
    }

}