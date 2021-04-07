package edu.marine.plantish1.posts;

import java.util.Date;

import edu.marine.plantish1.User;
import edu.marine.plantish1.plants.Plant;

public abstract class Post {
    User user;
    Date date;
    String image;
    Plant plant;

    public Post(User user, Date date, String image, Plant plant) {
        this.user = user;
        this.date = date;
        this.image = image;
        this.plant = plant;
    }
}
