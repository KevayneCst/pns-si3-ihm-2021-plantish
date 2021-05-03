package edu.polytech.si3.ihm.plantish.community;

public class FacebookModele {

    private String created_time;
    private String message;
    private String authorImage;
    private String name;
    private String postIV;


    // creating getter and setter methods.


    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostIV() {
        return postIV;
    }

    public void setPostIV(String postIV) {
        this.postIV = postIV;
    }

    // creating a constructor class.
    public FacebookModele(String authorImage, String name, String created_time,
                          String message, String postIV) {
        this.authorImage = authorImage;
        this.name = name;
        this.created_time = created_time;
        this.message = message;
        this.postIV = postIV;
    }
}
