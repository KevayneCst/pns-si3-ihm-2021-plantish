package edu.marine.plantish1.posts;

import java.util.Date;

import edu.marine.plantish1.User;
import edu.marine.plantish1.plants.Tree;

public class PostTree extends Post {
    private static final String TREEIMAGE = "linkTreeImage";


    public PostTree(User user, Date date, String image, Tree tree) {
        super(user, date, image, tree);
    }

    public PostTree(User user, Date date, Tree tree) {
        super(user, date, TREEIMAGE, tree);
    }
}
