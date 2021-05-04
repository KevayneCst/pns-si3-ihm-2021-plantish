package edu.polytech.si3.ihm.plantish.find;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

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

    /**
     * Mots clés pour recherche
     */
    private final List<String> keywords;

    public FilterData(Parcel in) {
        this.plantsShownIndex = in.readInt();
        this.plantsRangeIndex = in.readInt();
        this.keywords = in.createStringArrayList();
    }

    public FilterData(int plantsShownIndex, int plantsRangeIndex, List<String> keywords)  {
        this.plantsShownIndex = plantsShownIndex;
        this.plantsRangeIndex = plantsRangeIndex;
        this.keywords = keywords;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(plantsShownIndex);
        dest.writeInt(plantsRangeIndex);
        dest.writeList(keywords);
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

    public List<String> getKeywords() {
        return keywords;
    }

    public String toStringKeywords() {
        int len = keywords.size();
        if (len == 0 ) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < len ; i++) {
            sb.append(keywords.get(i));
            if (i != len-1) {
                sb.append(',');
            }
        }
        return sb.toString();
    }
}
