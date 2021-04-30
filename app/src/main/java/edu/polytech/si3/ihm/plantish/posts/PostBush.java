package edu.polytech.si3.ihm.plantish.posts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.Date;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.user.User;
import edu.polytech.si3.ihm.plantish.plants.Bush;


public class PostBush extends Post implements Serializable {
    private static final Bitmap BUSHIMAGE = BitmapFactory.decodeResource(MainActivity.context.getResources(), R.mipmap.grass);;

    public PostBush(User user, Date date, Bitmap image, Bush bush) {
        super(user, date, image, bush);

    }

    public PostBush(User user, Date date, Bush bush) {
        super(user, date, BUSHIMAGE, bush);
    }
}
