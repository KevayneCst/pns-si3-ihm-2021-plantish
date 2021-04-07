package edu.marine.plantish1.posts;

import java.util.Date;

import edu.marine.plantish1.User;
import edu.marine.plantish1.plants.Flower;

public class PostFlower extends Post {
    private static final String FLOWERIMAGE = "linkFlowerImage";


    public PostFlower(User user, Date date, String image, Flower flower) {
        super(user, date, image, flower);
    }

    public PostFlower(User user, Date date, Flower flower) {
        super(user, date, FLOWERIMAGE, flower);
    }
}
