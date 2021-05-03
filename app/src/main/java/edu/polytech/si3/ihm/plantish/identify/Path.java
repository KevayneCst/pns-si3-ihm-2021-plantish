package edu.polytech.si3.ihm.plantish.identify;

import android.content.Context;

import java.util.Arrays;

/**
 * This class is used for path to data in firebase Database
 */
public class Path {
    private String[] content;
    private String rawPath;
    public Path(String rawPath) {
        this.rawPath = rawPath;
        this.content = this.rawPath.split("/");
    }

    public String getChild(int i) {
        return content[i];
    }

    public void setContent(String[] content) {
        this.content = content;
    }

    public int getSize(){
        return content.length;
    }

    public Path add(String child){
        return new Path(this.rawPath+"/"+child);
    }


    public void removeLast(){
        setContent(Arrays.copyOfRange(content,0,getSize()-2));
    }

    public String toString(Context context) {
        try {
            return DataLoader.getCriterionDataFromJson(context, this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
