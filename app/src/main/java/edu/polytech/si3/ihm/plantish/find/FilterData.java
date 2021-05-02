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
    private final int plantsShownIndex;

    /**
     * Portée depuis la position actuelle de l'utilisateur, de la visibilité des plantes
     */
    private final int plantsRangeIndex;

    public FilterData(Parcel in) {
        this.plantsShownIndex = in.readInt();
        this.plantsRangeIndex = in.readInt();
    }

    public FilterData(int plantsShownIndex, int plantsRangeIndex)  {
        this.plantsShownIndex = plantsShownIndex;
        this.plantsRangeIndex = plantsRangeIndex;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(plantsShownIndex);
        dest.writeInt(plantsRangeIndex);
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

    public int getPlantsShownIndex() {
        return plantsShownIndex;
    }

    public int getPlantsRangeIndex() {
        return plantsRangeIndex;
    }
}
