package edu.polytech.si3.ihm.plantish.community;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.polytech.si3.ihm.plantish.R;

public class Profile extends AppCompatActivity {

    CircleImageView imgUsr;
    TextView username;
    Button history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imgUsr=findViewById(R.id.image_user);
        username=findViewById(R.id.username);
        history=findViewById(R.id.profile_btn);
        getUserData();
    }


    public void calendar(View v){
        Intent intent = new Intent(Profile.this,HitoriqueIncidents.class);
        startActivity(intent);
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
        Intent intent = new Intent(Profile.this, LoginActivity.class);
        startActivity(intent);
    }
}