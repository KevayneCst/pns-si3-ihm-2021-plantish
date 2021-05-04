package edu.polytech.si3.ihm.plantish.community;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.polytech.si3.ihm.plantish.R;

public class Profile extends Fragment {

    CircleImageView imgUsr;
    TextView username;
    Button history;
    private Context ctx ;
    private View view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ctx = getActivity();
        view = getView();
        super.onCreate(savedInstanceState);
        imgUsr= view.findViewById(R.id.image_user);
        username= view.findViewById(R.id.username);
        history= view.findViewById(R.id.profile_btn);
        getUserData();
    }


    public void calendar(View v){
        HitoriqueIncidents hitoriqueIncidents = new HitoriqueIncidents();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, hitoriqueIncidents);
        fragmentTransaction.commit();
    }

    public void getUserData(){
        ArrayList<FacebookModele> feeds = new ArrayList<>();
        AccessToken accessToken= AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    JSONObject image = object.getJSONObject("picture");
                    JSONObject imageData = image.getJSONObject("data");
                    String imgUrl = imageData.getString("url");
                    Picasso.get().load(imgUrl).into(imgUsr);
                    username.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void previous(View v){
        LoginActivity loginActivity = new LoginActivity();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, loginActivity);
        fragmentTransaction.commit();

    }
}