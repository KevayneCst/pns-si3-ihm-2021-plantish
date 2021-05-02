package edu.polytech.si3.ihm.plantish.find;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import edu.polytech.si3.ihm.plantish.R;

/**
 * @author Kévin Constantin
 */
public class FindPlantFilterActivity extends AppCompatActivity implements LinkedFilter {

    private final FilterData defaultData = new FilterData(100, 1000);
    private FilterData selectedData = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_filter);

        Spinner shownPlantsSpinner = findViewById(R.id.spinnerMaxShownPlants);
        ArrayAdapter<CharSequence> shownPlantsSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.plants_shown, android.R.layout.simple_spinner_item);
        shownPlantsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shownPlantsSpinner.setAdapter(shownPlantsSpinnerAdapter);

        Spinner rangePlantsSpinner = (Spinner) findViewById(R.id.spinnerMaxReachPlants);
        ArrayAdapter<CharSequence> rangePlantsSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.plants_range, android.R.layout.simple_spinner_item);
        rangePlantsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rangePlantsSpinner.setAdapter(rangePlantsSpinnerAdapter);

        findViewById(R.id.findFilterApply).setOnClickListener(v -> {
            Intent intent = new Intent(this, FindPlantActivity.class);
            intent.putExtra(KEYWORD_INTENT, selectedData == null ? defaultData : selectedData);
            startActivity(intent);
        });
    }
}
