package edu.polytech.si3.ihm.plantish.posts;

import java.util.Date;

import edu.polytech.si3.ihm.plantish.User;
import edu.polytech.si3.ihm.plantish.plants.Flower;


public class PostFlower extends Post {
    private static final String FLOWERIMAGE = "linkFlowerImage";


    public PostFlower(User user, Date date, String image, Flower flower) {
        super(user, date, image, flower);
    }

    public PostFlower(User user, Date date, Flower flower) {
        super(user, date, FLOWERIMAGE, flower);
    }
}
