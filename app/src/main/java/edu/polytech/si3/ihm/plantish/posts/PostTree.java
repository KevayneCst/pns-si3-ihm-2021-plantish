package edu.polytech.si3.ihm.plantish.posts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.Date;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.user.User;
import edu.polytech.si3.ihm.plantish.plants.Tree;

public class PostTree extends Post implements Serializable {
    private static final Bitmap TREEIMAGE = BitmapFactory.decodeResource(MainActivity.context.getResources(), R.mipmap.tree2);;


    public PostTree(User user, Date date, Bitmap image, Tree tree) {
        super(user, date, image, tree);
    }

    public PostTree(User user, Date date, Tree tree) {
        super(user, date, TREEIMAGE, tree);
    }
}
