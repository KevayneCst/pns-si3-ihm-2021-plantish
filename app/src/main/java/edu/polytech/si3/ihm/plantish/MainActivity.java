package edu.polytech.si3.ihm.plantish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.polytech.si3.ihm.plantish.community.FacebookFeed;
import edu.polytech.si3.ihm.plantish.community.LoginActivity;
import edu.polytech.si3.ihm.plantish.find.FindPlantActivity;
import edu.polytech.si3.ihm.plantish.identify.activities_identification.CriterionActivity;
import edu.polytech.si3.ihm.plantish.identify.activities_identification.ListOfPlantsFoundActivity;
import edu.polytech.si3.ihm.plantish.identify.activities_identification.PlantNotFoundActivity;
import edu.polytech.si3.ihm.plantish.identify.activities_identification.PlantTypeActivity;
import edu.polytech.si3.ihm.plantish.user.Session;
import edu.polytech.si3.ihm.plantish.user.User;

import static edu.polytech.si3.ihm.plantish.identify.Application.TAG;

public class MainActivity extends AppCompatActivity {

    public Session session;
    public static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();
        session = Session.getInstance();
        session.setUser(new User("Emilie"));

        setContentView(R.layout.activity_main);
        MyLocationListener.getLocation(this);

        setBottomNavigation(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MainFragment())
                .commit();

    }

    public void setBottomNavigation(Activity activity){
        BottomNavigationView navigationView = (BottomNavigationView) activity.findViewById(R.id.bottom_navigation);
    
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.page_0:
                        selectedFragment = new MainFragment();
                        break;
                    case R.id.page_1:
                        selectedFragment = new FindPlantActivity();
                        break;
                    case R.id.page_2:
                        selectedFragment = new AddPlantActivity();
                        break;
                    case R.id.page_3:
                        selectedFragment = new PlantTypeActivity();
                        break;
                    case R.id.page_4:
                        selectedFragment = new LoginActivity();
                        break;
    
                }

                item.setChecked(true);
                // It will help to replace the
                // one fragment to other.
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .addToBackStack(TAG)
                        .commit();
                return false;
            }});
    
    }

    public static void setMainFragment(FragmentManager fragmentManager){
        MainFragment mainFragment = new MainFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mainFragment)
                .commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (f instanceof CriterionActivity || f instanceof PlantNotFoundActivity || f instanceof ListOfPlantsFoundActivity) {//the fragment on which you want to handle your back press
            Log.i("BACK PRESSED", "BACK PRESSED");
        }else{
            super.onBackPressed();
        }
    }

}






