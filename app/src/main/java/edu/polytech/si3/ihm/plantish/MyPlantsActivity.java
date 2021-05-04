package edu.polytech.si3.ihm.plantish;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import edu.polytech.si3.ihm.plantish.posts.PostAdapter;
import edu.polytech.si3.ihm.plantish.user.Session;

public class MyPlantsActivity extends ListFragment {

    public Session session;

    private Context ctx ;
    private View view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_plants, container, false);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ctx = getActivity();
        view = getView();
        super.onCreate(savedInstanceState);

        this.session = Session.getInstance();
        PostAdapter postAdapter = new PostAdapter(ctx, session.getUserPosts());
        postAdapter.notifyDataSetChanged();

        setListAdapter(postAdapter);



    }
}
