package edu.polytech.si3.ihm.plantish.posts;

import java.util.Date;

import edu.polytech.si3.ihm.plantish.User;
import edu.polytech.si3.ihm.plantish.plants.Plant;


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
