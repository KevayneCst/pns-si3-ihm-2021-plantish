package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONException;

import java.util.List;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.identify.Criterion;
import edu.polytech.si3.ihm.plantish.identify.DataLoader;
import edu.polytech.si3.ihm.plantish.identify.PlantFinderService;
import edu.polytech.si3.ihm.plantish.identify.adapters_identification.GridViewCriteriaAdapter;
import edu.polytech.si3.ihm.plantish.identify.adapters_identification.ListViewCriteriaAdapter;
import edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria;

import static edu.polytech.si3.ihm.plantish.identify.Application.ACTION_RESP;
import static edu.polytech.si3.ihm.plantish.identify.Application.CRITERION_LABEL_VALUE;
import static edu.polytech.si3.ihm.plantish.identify.Application.FILTERED_PLANTS;
import static edu.polytech.si3.ihm.plantish.identify.Application.FILTERED_PLANTS_SERVICE;
import static edu.polytech.si3.ihm.plantish.identify.Application.PLANTS;
import static edu.polytech.si3.ihm.plantish.identify.Application.PLANT_CRITERION_LABEL;
import static edu.polytech.si3.ihm.plantish.identify.Application.VIEW_MODE_GRIDVIEW;
import static edu.polytech.si3.ihm.plantish.identify.Application.VIEW_MODE_LISTVIEW;
import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria.TYPE;

public abstract class CriterionActivity extends AppCompatActivity {

    protected PlantFoundReceiver receiver;
    protected String plants;
    protected String filteredPlants;
    protected PlantCriteria plantCriteria;
    protected String criterionLabelValue;
    protected ViewStub stubGrid;
    protected ViewStub stubList;
    protected ListView listView;
    protected GridView gridView;
    protected ListViewCriteriaAdapter listViewAdapter;
    protected GridViewCriteriaAdapter gridViewAdapter;
    protected List<Criterion> criterionList;
    protected TextView activityTitleListView;
    protected TextView activityTitleGridView;
    protected Button iDontKnowButtonListView;
    protected Button iDontKnowButtonGridView;
    protected Button plantFoundButtonListView;
    protected Button plantFoundButtonGridView;
    protected Button cancelButtonListView;
    protected Button cancelButtonGridView;
    protected int currentViewMode = 0;
    protected Intent intentToNextActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.criteria_layout);
        createActivity();
    }

    protected void createActivity(){


        stubList = findViewById(R.id.stub_list);
        stubGrid = findViewById(R.id.stub_grid);

        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = findViewById(R.id.listView);
        gridView = findViewById(R.id.gridview);

        activityTitleListView = findViewById(R.id.criterionLabelListView);
        activityTitleGridView = findViewById(R.id.criterionLabelGridView);
        iDontKnowButtonListView = findViewById(R.id.iDontKnowButtonListView);
        iDontKnowButtonGridView = findViewById(R.id.iDontKnowButtonGridView);
        plantFoundButtonListView = findViewById(R.id.plantFoundButtonListView);
        plantFoundButtonGridView = findViewById(R.id.plantFoundButtonGridView);
        cancelButtonListView = findViewById(R.id.cancelActionListView);
        cancelButtonGridView = findViewById(R.id.cancelActionGridView);


        //Get current view mode in share reference
        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);//Default is view listview
        //Register item click





    }

    protected void setActivityTitle(){
        String title;
        switch (plantCriteria){
            case TYPE:
                title = "Type"; break;
            case COLOR:
                title = "Couleur";break;
            case SEASON:
                title = "Période de floraison";break;
            case SUN:
                title = "Exposition au soleil";break;
            case SHAPE:
                title = "Forme des feuilles";break;
            case LAYOUT:
                title = "Disposition des feuilles";break;
            default:
                throw new IllegalStateException("Unexpected value: " + plantCriteria);
        }
        activityTitleListView.setText(title+" de la plante");
        activityTitleGridView.setText(title+" de la plante");

    }




    protected void sendIntentToService(){
        Intent intent = new Intent(CriterionActivity.this, PlantFinderService.class);
        intent.putExtra(PLANTS,plants);
        intent.putExtra(CRITERION_LABEL_VALUE, criterionLabelValue);
        intent.putExtra(PLANT_CRITERION_LABEL,plantCriteria.label);
        startService(intent);
    }

    protected void setReceiver(){
        receiver = new PlantFoundReceiver();
        IntentFilter filter = new IntentFilter(ACTION_RESP);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    protected void setPlants(){
        try {
            plants = (plantCriteria.label.equals(TYPE.label)) ? DataLoader.getPlantsDataFromJson(this) : getIntent().getStringExtra(FILTERED_PLANTS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setCriterionList(){ // To be overridden
    }

    protected void setPlantCriteria(){ // To be overridden
    }

    protected void setIntentToNextActivity(){ // To be overridden
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

    protected void setAdapters() {
        if(VIEW_MODE_LISTVIEW == currentViewMode) {
            listViewAdapter = new ListViewCriteriaAdapter(this, R.layout.criterion_list_item, criterionList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewCriteriaAdapter(this, R.layout.criterion_grid_item, criterionList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                criterionLabelValue = DataLoader.getCriterionDataFromJson(getApplicationContext(), criterionList.get(position).getName());
                sendIntentToService();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    protected void onClickIDontKnowButton(){
        startNextActivity();
        finish();
    }


    protected void setPlantFoundNumber(){
        try {
            int numberOfFoundPlants = DataLoader.getNumberOfPlantsFromJsonString(plants);
            plantFoundButtonListView.setText( (numberOfFoundPlants==1) ? numberOfFoundPlants+" plante trouvée" : numberOfFoundPlants+" plantes trouvées");
            plantFoundButtonGridView.setText( (numberOfFoundPlants==1) ? numberOfFoundPlants+" plante trouvée" : numberOfFoundPlants+" plantes trouvées");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * We will never get 0 plants when triggering this function, this is why testing if plants are empty has been omitted
     */
    protected void onClickFoundPlantsButton(){
        Intent intent = new Intent(getApplicationContext(), ListOfPlantsFoundActivity.class);
        intent.putExtra(FILTERED_PLANTS,plants);
        startActivity(intent);
    }


    public class PlantFoundReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            filteredPlants = intent.getStringExtra(FILTERED_PLANTS_SERVICE);
            System.out.println(filteredPlants);
            startNextActivity();
        }
    }

    public void startNextActivity(){
        if(filteredPlants==null) return;
        try {
            if (DataLoader.getNumberOfPlantsFromJsonString(filteredPlants)>0){
                intentToNextActivity.putExtra(FILTERED_PLANTS, filteredPlants);
            }
            else {
                intentToNextActivity = new Intent(getApplicationContext(), PlantNotFoundActivity.class);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(intentToNextActivity);
    }

    protected void onClickCancelButton(){
        onBackPressed();
    }



    protected void setButtons(){
        listView.setOnItemClickListener(onItemClick);
        gridView.setOnItemClickListener(onItemClick);
        iDontKnowButtonListView.setOnClickListener(click -> onClickIDontKnowButton());
        iDontKnowButtonGridView.setOnClickListener(click -> onClickIDontKnowButton());
        plantFoundButtonListView.setOnClickListener(click -> onClickFoundPlantsButton());
        plantFoundButtonGridView.setOnClickListener(click -> onClickFoundPlantsButton());
        cancelButtonListView.setOnClickListener(click -> onClickCancelButton());
        cancelButtonGridView.setOnClickListener(click -> onClickCancelButton());
    }

    @Override
    public void onStart() {
        setIntentToNextActivity();
        setPlantCriteria();
        setPlants();
        setCriterionList();
        setReceiver();
        setActivityTitle();
        switchView();
        setPlantFoundNumber();
        setButtons();

        super.onStart();
    }

    @Override
    public void onBackPressed() {
        if (plantCriteria.label.equals(TYPE.label)) startActivity(new Intent(this, MainActivity.class));
        startActivity(new Intent(this, PlantTypeActivity.class));
    }



}
