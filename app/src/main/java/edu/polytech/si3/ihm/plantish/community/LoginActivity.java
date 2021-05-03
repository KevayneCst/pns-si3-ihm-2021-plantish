package edu.polytech.si3.ihm.plantish.community;

import androidx.appcompat.app.AppCompatActivity;

import edu.polytech.si3.ihm.plantish.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.ShareButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private Button accessBtn;
    private TextView txtTop;
    ShareButton incident;
    ShareButton question;
    Button button;
    Button profileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton=findViewById(R.id.login_button);
        incident= findViewById(R.id.incident_button);
        question=findViewById(R.id.question_button);
        txtTop=findViewById(R.id.txtTop);
        accessBtn=findViewById(R.id.accessbtn);
        button=findViewById(R.id.community);
        profileBtn=findViewById(R.id.profile_btn);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setPermissions(Arrays.asList("user_gender,user_friends,user_posts"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Demo","Login success");

            }

            @Override
            public void onCancel() {
                Log.d("Demo","Login canceled");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Demo","Login error");
            }
        });
    }

    public void btnClick(View v){
        Intent intent = new Intent(LoginActivity.this, Posts.class);
        startActivity(intent);
    }

    public void accessCommunity(View v){
        Intent intent = new Intent(LoginActivity.this, FacebookFeed.class);
        startActivity(intent);
    }

    public void profile(View v){
        Intent intent = new Intent(LoginActivity.this,Profile.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);



        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    txtTop.setVisibility(txtTop.INVISIBLE);
                    profileBtn.setVisibility(profileBtn.VISIBLE);
                    button.setVisibility(button.VISIBLE);
                    accessBtn.setVisibility(accessBtn.VISIBLE);

                    JSONObject image = object.getJSONObject("picture");
                    JSONObject imageData = image.getJSONObject("data");
                    Toast.makeText(getApplicationContext(), "Login Success with facebook", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("fields","name,id,first_name,last_name,picture");
        graphRequest.setParameters(bundle);
        graphRequest.executeAsync();
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken==null){
                button.setVisibility(button.INVISIBLE);
                accessBtn.setVisibility(accessBtn.INVISIBLE);
                profileBtn.setVisibility(profileBtn.INVISIBLE);
                txtTop.setVisibility(txtTop.VISIBLE);
                profileBtn.setVisibility(profileBtn.INVISIBLE);
                LoginManager.getInstance().logOut();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }



}