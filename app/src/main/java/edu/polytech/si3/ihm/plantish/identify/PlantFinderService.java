package edu.polytech.si3.ihm.plantish.identify;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONException;

import static edu.polytech.si3.ihm.plantish.identify.Application.ACTION_RESP;
import static edu.polytech.si3.ihm.plantish.identify.Application.CRITERION_LABEL_VALUE;
import static edu.polytech.si3.ihm.plantish.identify.Application.FILTERED_PLANTS_SERVICE;
import static edu.polytech.si3.ihm.plantish.identify.Application.PLANTS;
import static edu.polytech.si3.ihm.plantish.identify.Application.PLANT_CRITERION_LABEL;

public class PlantFinderService extends IntentService {

    private String plants;
    private String plantCriterionLabel;
    private String criterionLabelValue;
    private String filteredPlants;

    public PlantFinderService() {
        super("PlantFinderService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        plants = intent.getStringExtra(PLANTS);
        plantCriterionLabel = intent.getStringExtra(PLANT_CRITERION_LABEL);
        criterionLabelValue = intent.getStringExtra(CRITERION_LABEL_VALUE);
        setFilteredPlants();
        broadcastResult();
    }

    private void setFilteredPlants(){
        try {
            filteredPlants =  DataLoader.getFiltredPlantsByCriterion(plants,plantCriterionLabel,criterionLabelValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void broadcastResult(){
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ACTION_RESP);
        broadcastIntent.putExtra(FILTERED_PLANTS_SERVICE,filteredPlants);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);

    }
}
