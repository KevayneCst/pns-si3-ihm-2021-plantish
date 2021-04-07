package edu.polytech.si3.ihm.plantish.posts;

import java.util.Date;

import edu.polytech.si3.ihm.plantish.User;
import edu.polytech.si3.ihm.plantish.plants.Bush;


public class PostBush extends Post {
    private static final String BUSHIMAGE = "linkBushImage";

    public PostBush(User user, Date date, String image, Bush bush) {
        super(user, date, image, bush);

    }

    public PostBush(User user, Date date, Bush bush) {
        super(user, date, BUSHIMAGE, bush);
    }
}
