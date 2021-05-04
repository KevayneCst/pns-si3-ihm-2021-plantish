package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.identify.lists_identification.ListType;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria.TYPE;

public class PlantTypeActivity extends CriterionActivity{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreateView(inflater, container, savedInstanceState);
        return view;
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
        fragmentToNextActivity = new PlantColorActivity();
    }



}

