package edu.polytech.si3.ihm.plantish;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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








       /* session = Session.getInstance();
        session.setUser(new User("Emilie"));

        setContentView(R.layout.activity_main);
        Button buttonAdd = (Button) findViewById(R.id.addButton);
        addListenerToAddPlant(buttonAdd);
    }

    private void addListenerToAddPlant(Button button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPlantActivity.class);
                startActivity(intent);
            }
        });
    }*/

}