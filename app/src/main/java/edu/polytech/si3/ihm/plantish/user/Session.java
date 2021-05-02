package edu.polytech.si3.ihm.plantish.user;

import android.content.Context;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.polytech.si3.ihm.plantish.AddPlantActivity;
import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.plants.Bush;
import edu.polytech.si3.ihm.plantish.plants.Flower;
import edu.polytech.si3.ihm.plantish.plants.Plant;
import edu.polytech.si3.ihm.plantish.plants.Tree;
import edu.polytech.si3.ihm.plantish.posts.Post;
import edu.polytech.si3.ihm.plantish.posts.PostBush;
import edu.polytech.si3.ihm.plantish.posts.PostFlower;
import edu.polytech.si3.ihm.plantish.posts.PostTree;

public class Session implements Serializable {
    private User user;
    private List<Post> appPosts;
    private static Session session;



    public Session(User user){
        Post[] posts;
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(MainActivity.context.getResources().openRawResource(R.raw.plants));
        posts = gson.fromJson(reader, Post[].class);

        // Post to child
        List<Post> postsNew = new LinkedList<>();
        for(Post post: posts){
            Plant plant = post.getPlant();
            if(post.getPlant().getTYPE().equals(Plant.BUSH))
                postsNew.add(new PostBush(post.getUser(), post.getDate(),  new Bush(plant.getLocation(), plant.getDescription(), plant.getFamily())));
            else if(post.getPlant().getTYPE().equals(Plant.TREE))
                postsNew.add(new PostTree(post.getUser(), post.getDate(),  new Tree(plant.getLocation(), plant.getDescription(), plant.getFamily())));
            else {
                postsNew.add(new PostFlower(post.getUser(), post.getDate(),  new Flower(plant.getLocation(), plant.getDescription(), plant.getFamily())));

            }
        }
        Collections.reverse(postsNew);
        appPosts = postsNew;
    }

    public static Session getInstance(){
        if(session == null){
           session = new Session(new User("Emilie"));
        }
        return session;
    }

    public User getUser(){
        return this.user;
    }

    public List<Post> getPosts(){
        return appPosts;
    }

    public void setUser(User user){
        this.user = user;
    }

    public static void addPost(Post post){
        getInstance().appPosts.add(0,post);
    }

    public static void deletePost(Post post) {getInstance().appPosts.remove(post);}

    public static void updatePost(int pos, Post newPost){
        getInstance().appPosts.remove(pos);
        getInstance().appPosts.add(pos, newPost);
    }

    public static Post getPost(int pos){ return getInstance().appPosts.get(pos);}
}
