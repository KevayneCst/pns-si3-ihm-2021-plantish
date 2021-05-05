package edu.polytech.si3.ihm.plantish.community;


import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.community.FacebookFeedAdapter;
import edu.polytech.si3.ihm.plantish.community.FacebookModele;

public class  FacebookFeed extends AppCompatActivity {

    TextView authorNameTV;
    CallbackManager callbackManager;
    final ContentValues event = new ContentValues();
    // creating variables for our requestqueue,
    // array list, progressbar, edittext,
    // image button and our recycler view.
    //private RequestQueue mRequestQueue;
    HashMap<Integer,String> imgPostLink = new HashMap<>();
    RecyclerView recyclerView;
    FacebookFeedAdapter adapter;
    CircleImageView authorImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_feed);
        authorNameTV=findViewById(R.id.idTVAuthorName);
        recyclerView=findViewById(R.id.idRVInstaFeeds);
        authorImage=findViewById(R.id.idCVAuthor);
        callbackManager = CallbackManager.Factory.create();

        getPostsImage();
        getFacebookFeeds();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void getFacebookFeeds(){
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
                    JSONObject posts = object.getJSONObject("posts");
                    JSONArray data = posts.getJSONArray("data");


                    //authorNameTV.setText(name);
                    for(int i=0;i<data.length();i++){
                        JSONObject element = data.getJSONObject(i);
                        String time = element.getString("created_time");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date d = sdf.parse(time);
                        String formattedTime = output.format(d);
                        String message = element.getString("message");
                        String id = element.getString("id");
                        String postImgLink = imgPostLink.get(i);
                        FacebookModele modele = new FacebookModele(imgUrl,name,formattedTime,message,postImgLink);
                        feeds.add(modele);
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
                FacebookFeedAdapter adapter = new FacebookFeedAdapter(feeds,FacebookFeed.this);
                RecyclerView instRV = findViewById(R.id.idRVInstaFeeds);

                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(FacebookFeed.this,RecyclerView.VERTICAL,false);

                instRV.setLayoutManager(linearLayoutManager);

                instRV.setAdapter(adapter);
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,posts,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void getPostsImage(){
        AccessToken accessToken= AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/me/posts",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        JSONObject image = response.getJSONObject();

                        try {
                            JSONArray images = image.getJSONArray("data");
                            Log.d("demo",images.get(0)+"");
                            for(int j=0;j<images.length();j++){
                                JSONObject element = images.getJSONObject(j);
                                String id = element.getString("id");
                                String full_image=element.getString("full_picture");
                                Log.d("demo",id);
                                Log.d("demo",full_image);
                                imgPostLink.put(j,full_image);
                                Log.d("demo1",imgPostLink.get(0));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "message,full_picture,created_time");
        request.setParameters(parameters);
        request.executeAsync();
    }
}