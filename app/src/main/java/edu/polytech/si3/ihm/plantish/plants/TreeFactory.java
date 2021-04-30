package edu.polytech.si3.ihm.plantish.plants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;

import java.io.IOException;
import java.util.Date;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.user.User;
import edu.polytech.si3.ihm.plantish.posts.Post;
import edu.polytech.si3.ihm.plantish.posts.PostTree;


public class TreeFactory extends PlantFactory {
    private Bitmap BITMAPTREE = BitmapFactory.decodeResource(MainActivity.context.getResources(), R.mipmap.tree2);

    @Override
    public Plant build(Location location, String family, String description){
        return new Tree(location, description, family);
    }

    @Override
    public Post build(User user, Date date, Bitmap image, Plant plant){
        PostTree postTree = new PostTree(user, date, image, (Tree) plant);
        super.savePost(postTree);
        return postTree;
    }

    @Override
    public Post build(User user, Date date, Plant plant){
        PostTree postTree = new PostTree(user, date, (Tree) plant);
        super.savePost(postTree);
        return postTree;
    }

}
