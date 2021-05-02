package edu.polytech.si3.ihm.plantish.find;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Axe : échanger un objet complexe entre activités
 * @author Kévin Constantin
 */
public class FilterData implements Parcelable {
    /**
     * Nombre de plantes qui seront affichées à l'écran
     */
    private final int plantsShown;

    /**
     * Portée depuis la position actuelle de l'utilisateur, de la visibilité des plantes
     */
    private final int plantsRange;

    public FilterData(Parcel in) {
        this.plantsShown = in.readInt();
        this.plantsRange = in.readInt();
    }

    public FilterData(int plantsShown, int plantsRange)  {
        this.plantsShown = plantsShown;
        this.plantsRange = plantsRange;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(plantsShown);
        dest.writeInt(plantsRange);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FilterData> CREATOR = new Creator<FilterData>() {
        @Override
        public FilterData createFromParcel(Parcel in) {
            return new FilterData(in);
        }

        @Override
        public FilterData[] newArray(int size) {
            return new FilterData[size];
        }
    };

    public int getPlantsShown() {
        return plantsShown;
    }

    public int getPlantsRange() {
        return plantsRange;
    }
}
