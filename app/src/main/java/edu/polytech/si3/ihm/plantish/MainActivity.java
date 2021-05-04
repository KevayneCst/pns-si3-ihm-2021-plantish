package edu.polytech.si3.ihm.plantish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.polytech.si3.ihm.plantish.find.FindPlantActivity;
import edu.polytech.si3.ihm.plantish.identify.activities_identification.PlantTypeActivity;
import edu.polytech.si3.ihm.plantish.user.Session;
import edu.polytech.si3.ihm.plantish.user.User;

public class MainActivity extends AppCompatActivity {

    public Session session;
    public static Context context;
    private ImageButton parametersButton;
    private ImageButton userButton;
    private ImageButton addButton;
    private ImageButton searchButton;
    private ImageButton communityButton;
    private ImageButton libraryButton;
    private ImageButton bugButton;
    private ImageButton infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();

        session.setUser(new User("Emilie"));

        setContentView(R.layout.activity_main);

        setBottomNavigation(this);


        //getting widgets
        parametersButton = findViewById(R.id.mainParametersButton);
        userButton = findViewById(R.id.mainUserButton);
        addButton = findViewById(R.id.mainAddButton);
        searchButton = findViewById(R.id.mainSearchButton);
        communityButton = findViewById(R.id.mainCommunityButton);
        libraryButton = findViewById(R.id.mainLibraryButton);
        bugButton = findViewById(R.id.mainBugButton);
        infoButton = findViewById(R.id.mainIButton);

        setOnClickButtons();
    }

    private void setOnClickButtons(){
        parametersButton.setOnClickListener(click -> onClickParametersButton());
        userButton.setOnClickListener(click -> onClickUserButton());
        addButton.setOnClickListener(click -> onClickAddButton());
        searchButton.setOnClickListener(click -> onClickSearchButton());
        communityButton.setOnClickListener(click -> onClickCommunityButton());
        libraryButton.setOnClickListener(click -> onClickLibraryButton());
        bugButton.setOnClickListener(click -> onClickBugButton());
        infoButton.setOnClickListener(click -> onClickInfoButton());
    }

    private void onClickParametersButton(){

    }

    private void onClickUserButton(){

    }

    private void onClickAddButton(){

    }

    private void onClickSearchButton(){

    }

    private void onClickCommunityButton(){

    }

    private void onClickLibraryButton(){

    }

    private void onClickBugButton(){

    }

    private void onClickInfoButton(){

    }






    public void setBottomNavigation(Activity activity){
        BottomNavigationView navigationView = (BottomNavigationView) activity.findViewById(R.id.bottom_navigation);
    
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.page_1:
                        selectedFragment = new FindPlantActivity();
                        break;
                    case R.id.page_2:
                        selectedFragment = new AddPlantActivity();
                        break;
    
                }
                // It will help to replace the
                // one fragment to other.
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return false;
            }});
    
    }


}






