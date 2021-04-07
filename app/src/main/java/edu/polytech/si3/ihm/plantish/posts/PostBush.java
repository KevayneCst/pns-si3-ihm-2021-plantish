package edu.marine.plantish1.posts;

import java.util.Date;

import edu.marine.plantish1.User;
import edu.marine.plantish1.plants.Bush;

public class PostBush extends Post {
    private static final String BUSHIMAGE = "linkBushImage";

    public PostBush(User user, Date date, String image, Bush bush) {
        super(user, date, image, bush);

    }

    public PostBush(User user, Date date, Bush bush) {
        super(user, date, BUSHIMAGE, bush);
    }
}
