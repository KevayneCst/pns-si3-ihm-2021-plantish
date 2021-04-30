package edu.polytech.si3.ihm.plantish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    }

}