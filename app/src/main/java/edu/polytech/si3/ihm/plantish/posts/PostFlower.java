package edu.polytech.si3.ihm.plantish.posts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.Date;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.user.User;
import edu.polytech.si3.ihm.plantish.plants.Flower;


public class PostFlower extends Post implements Serializable {
    private static final Bitmap FLOWERIMAGE = BitmapFactory.decodeResource(MainActivity.context.getResources(), R.mipmap.flower);;


    public PostFlower(User user, Date date, Bitmap image, Flower flower) {
        super(user, date, image, flower);
    }

    public PostFlower(User user, Date date, Flower flower) {
        super(user, date, FLOWERIMAGE, flower);
    }
}
