package edu.polytech.si3.ihm.plantish.community;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.posts.Post;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.ShareButton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends Fragment {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private Button accessBtn;
    private TextView txtTop;
    Button button;
    Button profileBtn;
    private Context ctx ;
    private View view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);
        loginButton=view.findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        txtTop=view.findViewById(R.id.txtTop);
        accessBtn=view.findViewById(R.id.accessbtn);
        button=view.findViewById(R.id.community);
        profileBtn=view.findViewById(R.id.profile_btn);
        loginButton.setFragment(this);
        loginButton.setPermissions(Arrays.asList("email,public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Demo","Login success");
                txtTop.setVisibility(txtTop.INVISIBLE);
                profileBtn.setVisibility(profileBtn.VISIBLE);
                button.setVisibility(button.VISIBLE);
                accessBtn.setVisibility(accessBtn.VISIBLE);
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
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ctx = getActivity();
        view = getView();
        super.onCreate(savedInstanceState);

        txtTop.setVisibility(txtTop.INVISIBLE);
        profileBtn.setVisibility(profileBtn.VISIBLE);
        button.setVisibility(button.VISIBLE);
        accessBtn.setVisibility(accessBtn.VISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Posts posts= new Posts();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, posts);
                fragmentTransaction.commit();
            }
        });


        accessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),FacebookFeed.class);
                startActivity(intent);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile profile = new Profile();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, profile);
                fragmentTransaction.commit();
            }
        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {


                    JSONObject image = object.getJSONObject("picture");
                    JSONObject imageData = image.getJSONObject("data");
                    Toast.makeText(ctx.getApplicationContext(), "Login Success with facebook", Toast.LENGTH_SHORT).show();

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
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}