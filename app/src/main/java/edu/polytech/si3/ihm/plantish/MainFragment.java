package edu.polytech.si3.ihm.plantish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.polytech.si3.ihm.plantish.community.LoginActivity;

public class MainFragment extends Fragment {

    private ImageButton infoButton;
    private ImageButton loginButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        //Exemple d'instanciation d'éléments graphiques. Ils devraient être ceux de votre activité.

        return view;
    }


    private Context ctx ;
    private View view;
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ctx = getActivity();
        view = getView();
        //getting widgets

        infoButton = view.findViewById(R.id.mainInfoButton);
        loginButton = view.findViewById(R.id.mainLoginButton);

        setOnClickButtons();
    }

    private void setOnClickButtons(){
        infoButton.setOnClickListener(click -> onClickInfoButton());
        loginButton.setOnClickListener(click -> onClickLoginButton());
    }


    private void onClickInfoButton(){

    }

    private void onClickLoginButton(){
        LoginActivity login= new LoginActivity();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, login);
        fragmentTransaction.commit();
    }
}
