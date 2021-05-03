package edu.polytech.si3.ihm.plantish.identify;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import static edu.polytech.si3.ihm.plantish.identify.Application.*;

public class PlantFound implements Parcelable {

    private String label;
    private String type;
    private String color;
    private String season;
    private String sun;
    private String shape;
    private String layout;
    private String picture;

    public PlantFound(String label, String type, String color, String season, String sun, String shape, String layout, String picture) {
        this.label = label;
        this.type = type;
        this.color = color;
        this.season = season;
        this.sun = sun;
        this.shape = shape;
        this.layout = layout;
        this.picture = picture;
    }

    public PlantFound(JSONObject jsonOfPlant){
        try {
            this.label = jsonOfPlant.getString(LABEL);
            this.type = jsonOfPlant.getString(TYPE);
            this.color = jsonOfPlant.getString(COLOR);
            this.season = jsonOfPlant.getString(SEASON);
            this.sun = jsonOfPlant.getString(SUN);
            this.shape = jsonOfPlant.getString(SHAPE);
            this.layout = jsonOfPlant.getString(LAYOUT);
            this.picture = jsonOfPlant.getString(PATH);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected PlantFound(Parcel in) {
        label = in.readString();
        type = in.readString();
        color = in.readString();
        season = in.readString();
        sun = in.readString();
        shape = in.readString();
        layout = in.readString();
        picture = in.readString();
    }

    public static final Creator<PlantFound> CREATOR = new Creator<PlantFound>() {
        @Override
        public PlantFound createFromParcel(Parcel in) {
            return new PlantFound(in);
        }

        @Override
        public PlantFound[] newArray(int size) {
            return new PlantFound[size];
        }
    };

    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public String getColor() {
        return color;
    }

    public String getSeason() {
        return season;
    }

    public String getSun() {
        return sun;
    }

    public String getShape() {
        return shape;
    }

    public String getLayout() {
        return layout;
    }

    public String getPicture() {
        return picture;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeString(type);
        dest.writeString(color);
        dest.writeString(season);
        dest.writeString(sun);
        dest.writeString(shape);
        dest.writeString(layout);
        dest.writeString(picture);
    }
}
