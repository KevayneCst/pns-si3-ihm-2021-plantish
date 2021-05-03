package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.identify.PlantFound;
import edu.polytech.si3.ihm.plantish.identify.adapters_identification.GridViewPlantFoundAdapter;
import edu.polytech.si3.ihm.plantish.identify.adapters_identification.ListViewPlantFoundAdapter;

import static edu.polytech.si3.ihm.plantish.identify.Application.FILTERED_PLANTS;
import static edu.polytech.si3.ihm.plantish.identify.Application.PLANT_FOUND;
import static edu.polytech.si3.ihm.plantish.identify.Application.VIEW_MODE_GRIDVIEW;
import static edu.polytech.si3.ihm.plantish.identify.Application.VIEW_MODE_LISTVIEW;

public class ListOfPlantsFoundActivity extends AppCompatActivity {

    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private ListViewPlantFoundAdapter listViewAdapter;
    private GridViewPlantFoundAdapter gridViewAdapter;
    private List<PlantFound> plantFoundList;
    private int currentViewMode = 0;
    private String plants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found_plants);

        stubList = findViewById(R.id.stub_list);
        stubGrid = findViewById(R.id.stub_grid);

        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = findViewById(R.id.plantFoundListView);
        gridView = findViewById(R.id.plantFoundGridView);

        setPlants();

        //get list of found plants
        try {
            getPlantFoundList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Get current view mode in share reference
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        //Register item click
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();

    }

    public void setPlants() {
        plants = getIntent().getStringExtra(FILTERED_PLANTS);
    }

    private void switchView() {

        if(VIEW_MODE_LISTVIEW == currentViewMode) {
            //Display listview
            stubList.setVisibility(View.VISIBLE);
            //Hide gridview
            stubGrid.setVisibility(View.GONE);
        } else {
            //Hide listview
            stubList.setVisibility(View.GONE);
            //Display gridview
            stubGrid.setVisibility(View.VISIBLE);
        }
        setAdapters();
    }

    private void setAdapters() {
        if(VIEW_MODE_LISTVIEW == currentViewMode) {
            listViewAdapter = new ListViewPlantFoundAdapter(this, R.layout.plant_found_list_item, plantFoundList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewPlantFoundAdapter(this, R.layout.plant_found_grid_item, plantFoundList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    public List<PlantFound> getPlantFoundList() throws JSONException {
        plantFoundList = new ArrayList<>();
        JSONObject obj = new JSONObject(plants);
        JSONArray jsonArray = obj.getJSONArray("Plants");
        for (int i = 0;i<jsonArray.length();i++){
            plantFoundList.add(new PlantFound(jsonArray.getJSONObject(i)));
        }
        return plantFoundList;
    }


    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Do when user click to item
            Intent intent = new Intent(getApplicationContext(), PlantFoundActivity.class);
            intent.putExtra(PLANT_FOUND, plantFoundList.get(position));
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_1:
                if(VIEW_MODE_LISTVIEW == currentViewMode) {
                    currentViewMode = VIEW_MODE_GRIDVIEW;
                } else {
                    currentViewMode = VIEW_MODE_LISTVIEW;
                }
                //Switch view
                switchView();
                //Save view mode in share reference
                SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentViewMode", currentViewMode);
                editor.commit();

                break;
        }
        return true;
    }
}
