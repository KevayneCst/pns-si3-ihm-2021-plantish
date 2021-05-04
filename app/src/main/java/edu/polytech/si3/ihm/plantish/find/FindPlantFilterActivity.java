package edu.polytech.si3.ihm.plantish.find;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import edu.polytech.si3.ihm.plantish.R;

/**
 * @author Kévin Constantin
 */
public class FindPlantFilterActivity extends Fragment implements LinkedFilter {

    public static final FilterData defaultData = new FilterData(0, 0, new ArrayList<>());
    private static FilterData selectedData = null;
    private View view;
    private Context cxt;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_find_filter, container, false);
        super.onCreate(savedInstanceState);
        cxt = getActivity();

        TextView textViewKeywords = view.findViewById(R.id.findFilterWithKeyword);
        textViewKeywords.setText(selectedData == null ? defaultData.toStringKeywords() : selectedData.toStringKeywords());
        textViewKeywords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int indexShown = selectedData == null ? defaultData.getPlantsShownIndex() : selectedData.getPlantsShownIndex();
                int indexRange = selectedData == null ? defaultData.getPlantsRangeIndex() : selectedData.getPlantsRangeIndex();
                selectedData = new FilterData(indexShown, indexRange, parseInputText(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        Spinner shownPlantsSpinner = view.findViewById(R.id.spinnerMaxShownPlants);
        ArrayAdapter<CharSequence> shownPlantsSpinnerAdapter = ArrayAdapter.createFromResource(cxt, R.array.plants_shown, android.R.layout.simple_spinner_item);
        shownPlantsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shownPlantsSpinner.setAdapter(shownPlantsSpinnerAdapter);
        shownPlantsSpinner.setSelection(selectedData == null ? defaultData.getPlantsShownIndex() : selectedData.getPlantsShownIndex());
        shownPlantsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int indexRange = selectedData == null ? defaultData.getPlantsRangeIndex() : selectedData.getPlantsRangeIndex();
                List<String> keywords = selectedData == null ? defaultData.getKeywords() : selectedData.getKeywords();
                selectedData = new FilterData(position, indexRange, keywords);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner rangePlantsSpinner = (Spinner) view.findViewById(R.id.spinnerMaxReachPlants);
        ArrayAdapter<CharSequence> rangePlantsSpinnerAdapter = ArrayAdapter.createFromResource(cxt, R.array.plants_range, android.R.layout.simple_spinner_item);
        rangePlantsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rangePlantsSpinner.setAdapter(rangePlantsSpinnerAdapter);
        rangePlantsSpinner.setSelection(selectedData == null ? defaultData.getPlantsRangeIndex() : selectedData.getPlantsRangeIndex());
        rangePlantsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int indexShown = selectedData == null ? defaultData.getPlantsShownIndex() : selectedData.getPlantsShownIndex();
                List<String> keywords = selectedData == null ? defaultData.getKeywords() : selectedData.getKeywords();
                selectedData = new FilterData(indexShown, position, keywords);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        view.findViewById(R.id.findFilterApply).setOnClickListener(v -> {

            FragmentManager fragmentManager = getFragmentManager();
            Fragment fragmentMap = new FindPlantActivity();
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEYWORD_INTENT, selectedData == null ? defaultData : selectedData);
            fragmentMap.setArguments(bundle);

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragmentMap);
            fragmentTransaction.commit();
        });

        view.findViewById(R.id.findFilterDefault).setOnClickListener(v -> {
            selectedData = null;
            shownPlantsSpinner.setSelection(defaultData.getPlantsShownIndex());
            rangePlantsSpinner.setSelection(defaultData.getPlantsRangeIndex());
            textViewKeywords.setText(defaultData.toStringKeywords());
        });
        return view;
    }

    private List<String> parseInputText(String s) {
        List<String> keywords = new ArrayList<>();
        String trimming = s.trim();
        if (trimming.isEmpty()) {
            return keywords;
        }
        String[] spliced = trimming.split(",");
        if (spliced.length == 0) {
            return keywords;
        }
        for (String split : spliced) {
            String tmp = split.trim();
            if (!tmp.isEmpty()) {
                keywords.add(tmp);
            }
        }
        return keywords;
    }
}
