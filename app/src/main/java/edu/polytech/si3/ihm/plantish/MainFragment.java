package edu.polytech.si3.ihm.plantish;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    private ImageButton parametersButton;
    private ImageButton userButton;
    private ImageButton addButton;
    private ImageButton searchButton;
    private ImageButton communityButton;
    private ImageButton libraryButton;
    private ImageButton bugButton;
    private ImageButton infoButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.old_main_fragment, container, false);
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
        parametersButton = view.findViewById(R.id.mainParametersButton);
        userButton = view.findViewById(R.id.mainUserButton);
        addButton = view.findViewById(R.id.mainAddButton);
        searchButton = view.findViewById(R.id.mainSearchButton);
        communityButton = view.findViewById(R.id.mainCommunityButton);
        libraryButton = view.findViewById(R.id.mainLibraryButton);
        bugButton = view.findViewById(R.id.mainBugButton);
        infoButton = view.findViewById(R.id.mainIButton);

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
}
