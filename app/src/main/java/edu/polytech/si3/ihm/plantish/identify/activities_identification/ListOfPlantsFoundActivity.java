package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.identify.DataLoader;
import edu.polytech.si3.ihm.plantish.identify.PlantFound;
import edu.polytech.si3.ihm.plantish.identify.adapters_identification.GridViewPlantFoundAdapter;
import edu.polytech.si3.ihm.plantish.identify.adapters_identification.ListViewPlantFoundAdapter;

import static android.content.Context.MODE_PRIVATE;
import static edu.polytech.si3.ihm.plantish.identify.Application.FILTERED_PLANTS;
import static edu.polytech.si3.ihm.plantish.identify.Application.PLANT_FOUND;
import static edu.polytech.si3.ihm.plantish.identify.Application.TAG;
import static edu.polytech.si3.ihm.plantish.identify.Application.VIEW_MODE_GRIDVIEW;
import static edu.polytech.si3.ihm.plantish.identify.Application.VIEW_MODE_LISTVIEW;

public class ListOfPlantsFoundActivity extends Fragment {

    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private ListViewPlantFoundAdapter listViewAdapter;
    private GridViewPlantFoundAdapter gridViewAdapter;
    private List<PlantFound> plantFoundList;
    private TextView numberOfPlantsListView;
    private TextView numberOfPlantsGridView;
    private Button notMyPLantButtonListView;
    private Button notMyPLantButtonGridView;
    private Button mainMenuButtonListView;
    private Button mainMenuButtonGridView;
    private int currentViewMode = 0;
    private String plants;
    private View view;
    private Activity ctx;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.activity_found_plants, container, false);
        ctx = getActivity();

        setHasOptionsMenu(true);

        stubList = view.findViewById(R.id.stub_list);
        stubGrid = view.findViewById(R.id.stub_grid);

        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = view.findViewById(R.id.plantFoundListView);
        gridView = view.findViewById(R.id.plantFoundGridView);

        //get widgets
        numberOfPlantsListView = view.findViewById(R.id.numberOfPlantsListView);
        numberOfPlantsGridView = view.findViewById(R.id.numberOfPlantsGridView);
        notMyPLantButtonListView = view.findViewById(R.id.notMyPLantButtonListView);
        notMyPLantButtonGridView = view.findViewById(R.id.notMyPLantButtonGridView);
        mainMenuButtonListView = view.findViewById(R.id.mainMenuButtonListView);
        mainMenuButtonGridView = view.findViewById(R.id.mainMenuButtonGridView);

        setPlants();

        //get list of found plants

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            plants = bundle.getString(FILTERED_PLANTS, "");
        }

        try {
            getPlantFoundList();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Get current view mode in share reference
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        //Register item click
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);

        switchView();

        setNumberOfPlants();
        notMyPLantButtonListView.setOnClickListener(click -> goToNotFoundActivity());
        notMyPLantButtonGridView.setOnClickListener(click -> goToNotFoundActivity());
        mainMenuButtonListView.setOnClickListener(click -> goToMainActivity());
        mainMenuButtonGridView.setOnClickListener(click -> goToMainActivity());

        return view;
    }

    private void setNumberOfPlants(){
        String textToBeSet;
        int numberOfPlantsFound = 1;
        try {
            numberOfPlantsFound = DataLoader.getNumberOfPlantsFromJsonString(plants);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        textToBeSet = (numberOfPlantsFound==1) ? "Une plante correspond à ces critères" : numberOfPlantsFound+" plantes correspondent à ces critères";
        numberOfPlantsListView.setText(textToBeSet);
        numberOfPlantsGridView.setText(textToBeSet);
    }

    private void goToMainActivity(){
        MainActivity.setMainFragment(((AppCompatActivity)ctx).getSupportFragmentManager());
    }

    private void goToNotFoundActivity(){
        startActivity(new Intent(ctx.getApplicationContext(), PlantNotFoundActivity.class));
    }

    private void setPlants() {
        Bundle bundle = new Bundle();
        plants = bundle.getString(FILTERED_PLANTS);
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
            listViewAdapter = new ListViewPlantFoundAdapter(ctx, R.layout.plant_found_list_item, plantFoundList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewPlantFoundAdapter(ctx, R.layout.plant_found_grid_item, plantFoundList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    public List<PlantFound> getPlantFoundList() throws JSONException {
        plantFoundList = new ArrayList<>();
        Log.d("PLANTS", plants+" ");
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
            PlantFoundActivity plantFoundActivity = new PlantFoundActivity();
            Bundle bundle = new Bundle();
            bundle.putParcelable(PLANT_FOUND, plantFoundList.get(position));
            plantFoundActivity.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, plantFoundActivity).addToBackStack(TAG);
            fragmentTransaction.commit();

        }
    };

    @Override
    public void  onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu,inflater);
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
                SharedPreferences sharedPreferences = ctx.getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("currentViewMode", currentViewMode);
                editor.commit();

                break;
        }
        return true;
    }
}
