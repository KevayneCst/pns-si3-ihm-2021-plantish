package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONException;

import java.util.List;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.MainFragment;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.community.LoginActivity;
import edu.polytech.si3.ihm.plantish.identify.Criterion;
import edu.polytech.si3.ihm.plantish.identify.DataLoader;
import edu.polytech.si3.ihm.plantish.identify.PlantFinderService;
import edu.polytech.si3.ihm.plantish.identify.adapters_identification.GridViewCriteriaAdapter;
import edu.polytech.si3.ihm.plantish.identify.adapters_identification.ListViewCriteriaAdapter;
import edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria;

import static android.content.Context.MODE_PRIVATE;
import static edu.polytech.si3.ihm.plantish.identify.Application.ACTION_RESP;
import static edu.polytech.si3.ihm.plantish.identify.Application.CRITERION_LABEL_VALUE;
import static edu.polytech.si3.ihm.plantish.identify.Application.FILTERED_PLANTS;
import static edu.polytech.si3.ihm.plantish.identify.Application.FILTERED_PLANTS_SERVICE;
import static edu.polytech.si3.ihm.plantish.identify.Application.PLANTS;
import static edu.polytech.si3.ihm.plantish.identify.Application.PLANT_CRITERION_LABEL;
import static edu.polytech.si3.ihm.plantish.identify.Application.VIEW_MODE_GRIDVIEW;
import static edu.polytech.si3.ihm.plantish.identify.Application.VIEW_MODE_LISTVIEW;
import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantCriteria.TYPE;

public abstract class CriterionActivity extends Fragment {

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
    protected Fragment fragmentToNextActivity;

    protected Context ctx;
    protected View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.criteria_layout, container, false);

        super.onCreate(savedInstanceState);
        ctx = getActivity();
        createActivity();
        setHasOptionsMenu(true);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    protected void createActivity(){

        stubList = view.findViewById(R.id.stub_list);
        stubGrid = view.findViewById(R.id.stub_grid);



        //Inflate ViewStub before get view

        stubList.inflate();
        stubGrid.inflate();

        listView = view.findViewById(R.id.listView);
        gridView = view.findViewById(R.id.gridview);

        activityTitleListView = view.findViewById(R.id.numberOfPlantsListView);
        activityTitleGridView = view.findViewById(R.id.criterionLabelGridView);
        iDontKnowButtonListView = view.findViewById(R.id.notMyPLantButtonListView);
        iDontKnowButtonGridView = view.findViewById(R.id.iDontKnowButtonGridView);
        plantFoundButtonListView = view.findViewById(R.id.mainMenuButtonListView);
        plantFoundButtonGridView = view.findViewById(R.id.plantFoundButtonGridView);
        cancelButtonListView = view.findViewById(R.id.cancelActionListView);
        cancelButtonGridView = view.findViewById(R.id.cancelActionGridView);


        //Get current view mode in share reference
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("ViewMode", MODE_PRIVATE);
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
        Intent intent = new Intent(getActivity(), PlantFinderService.class);
        intent.putExtra(PLANTS,plants);
        intent.putExtra(CRITERION_LABEL_VALUE, criterionLabelValue);
        intent.putExtra(PLANT_CRITERION_LABEL,plantCriteria.label);
        ctx.startService(intent);
    }

    protected void setReceiver(){
        receiver = new PlantFoundReceiver();
        IntentFilter filter = new IntentFilter(ACTION_RESP);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, filter);
    }

    protected void setPlants(){
        Bundle bundle = this.getArguments();

        try {
            plants = (inTypeActivity()) ? DataLoader.getPlantsDataFromJson(ctx) : bundle.getString(FILTERED_PLANTS);
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
            listViewAdapter = new ListViewCriteriaAdapter(ctx, R.layout.criterion_list_item, criterionList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdapter = new GridViewCriteriaAdapter(ctx, R.layout.criterion_grid_item, criterionList);
            gridView.setAdapter(gridViewAdapter);
        }
    }

    AdapterView.OnItemClickListener onItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                criterionLabelValue = DataLoader.getCriterionDataFromJson(ctx.getApplicationContext(), criterionList.get(position).getName());
                sendIntentToService();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    protected void onClickIDontKnowButton(){
        startNextActivity(plants);
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
        ListOfPlantsFoundActivity listOfPlantsFoundActivity = new ListOfPlantsFoundActivity();
        Bundle bundle = new Bundle();
        bundle.putSerializable(FILTERED_PLANTS, plants);
        listOfPlantsFoundActivity.setArguments(bundle);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, listOfPlantsFoundActivity);
        fragmentTransaction.commit();
    }


    public class PlantFoundReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            filteredPlants = intent.getStringExtra(FILTERED_PLANTS_SERVICE);
            System.out.println(filteredPlants);
            Log.d("FILTRE", filteredPlants);
            startNextActivity(filteredPlants);
        }
    }

    public void startNextActivity(String filteredPlants){
        Log.d("FRAG", fragmentToNextActivity.toString());
        try {
            if (DataLoader.getNumberOfPlantsFromJsonString(filteredPlants)>0){
                Bundle bundle = new Bundle();
                bundle.putSerializable(FILTERED_PLANTS, filteredPlants);
                fragmentToNextActivity.setArguments(bundle);
            }
            else {
                fragmentToNextActivity = new PlantNotFoundActivity();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = ((AppCompatActivity)ctx).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragmentToNextActivity);
        //fragmentTransaction.commit();
        fragmentTransaction.commitAllowingStateLoss();
    }


    protected void onClickCancelButton(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (inTypeActivity()){
            MainFragment mainActivity = new MainFragment();
            fragmentTransaction.replace(R.id.fragment_container, mainActivity);
        }
        else {
            PlantTypeActivity plantTypeActivity = new PlantTypeActivity();
            fragmentTransaction.replace(R.id.fragment_container, plantTypeActivity);
        }
        fragmentTransaction.commit();
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



    private Boolean inTypeActivity(){
        return (plantCriteria.label.equals(TYPE.label));
    }




}
