package edu.polytech.si3.ihm.plantish.posts;

import java.util.Date;

import edu.polytech.si3.ihm.plantish.User;
import edu.polytech.si3.ihm.plantish.plants.Tree;

public class PostTree extends Post{
    private static final String TREEIMAGE = "linkTreeImage";


    public PostTree(User user, Date date, String image, Tree tree) {
        super(user, date, image, tree);
    }

    public PostTree(User user, Date date, Tree tree) {
        super(user, date, TREEIMAGE, tree);
    }
}
