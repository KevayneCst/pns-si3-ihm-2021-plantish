package edu.polytech.si3.ihm.plantish;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import edu.polytech.si3.ihm.plantish.posts.PostAdapter;
import edu.polytech.si3.ihm.plantish.user.Session;

public class MyPlantsActivity extends ListActivity {

    public Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_plants);

        this.session = Session.getInstance();
        PostAdapter postAdapter = new PostAdapter(this, session.getPosts());
        setListAdapter(postAdapter);



    }
}
