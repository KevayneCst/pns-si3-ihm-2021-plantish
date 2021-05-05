package edu.polytech.si3.ihm.plantish.identify.activities_identification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.community.LoginActivity;


public class PlantNotFoundActivity extends Fragment {

    private Button askCommunityButton;
    private Button homeButton;
    private View view;
    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_plant_not_found, container, false);
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ctx = getActivity();
        view = getView();
        askCommunityButton =  view.findViewById(R.id.askCommunityButton);
        homeButton = view.findViewById(R.id.mainButton);
        askCommunityButton.setOnClickListener(click -> onClickAskCommunityButton());
        homeButton.setOnClickListener(click -> onClickHomeButton());
    }

    private void onClickAskCommunityButton(){
        LoginActivity login= new LoginActivity();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, login);
        fragmentTransaction.commit();
    }

    private void onClickHomeButton(){
        startActivity(new Intent(ctx, MainActivity.class));
    }

}