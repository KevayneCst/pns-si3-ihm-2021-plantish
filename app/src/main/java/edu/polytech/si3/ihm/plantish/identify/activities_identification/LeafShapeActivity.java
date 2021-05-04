package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.polytech.si3.ihm.plantish.identify.lists_identification.ListShape;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria.SHAPE;

public class LeafShapeActivity extends CriterionActivity {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateView(inflater, container, savedInstanceState);

        return view;
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
        fragmentToNextActivity = new LeafLayoutActivity();
    }

}