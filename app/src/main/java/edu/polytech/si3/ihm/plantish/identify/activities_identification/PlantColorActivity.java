package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.polytech.si3.ihm.plantish.identify.lists_identification.ListColor;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria.COLOR;

public class PlantColorActivity extends CriterionActivity{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateView(inflater, container, savedInstanceState);

        return view;
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
        fragmentToNextActivity = new PlantSeasonActivity();
    }
}