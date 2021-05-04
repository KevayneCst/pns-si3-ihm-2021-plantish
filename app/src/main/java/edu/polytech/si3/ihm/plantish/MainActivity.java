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

import com.google.android.material.bottomnavigation.BottomNavigationView;

import edu.polytech.si3.ihm.plantish.find.FindPlantActivity;
import edu.polytech.si3.ihm.plantish.identify.activities_identification.PlantTypeActivity;
import edu.polytech.si3.ihm.plantish.user.Session;
import edu.polytech.si3.ihm.plantish.user.User;

public class MainActivity extends AppCompatActivity {

    public Session session;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.context = getApplicationContext();


        session = Session.getInstance();
        session.setUser(new User("Emilie"));

        setContentView(R.layout.activity_main);

        setBottomNavigation(this);
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