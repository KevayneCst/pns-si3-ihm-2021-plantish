package edu.polytech.si3.ihm.plantish.identify.adapters_identification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.identify.DataLoader;
import edu.polytech.si3.ihm.plantish.identify.PlantFound;

public class GridViewPlantFoundAdapter extends ArrayAdapter<PlantFound> {

    private Context context;

    public GridViewPlantFoundAdapter(Context context, int resource, List<PlantFound> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.plant_found_grid_item, null);
        }
        PlantFound plantFound = getItem(position);
        ImageView plantFoundPicture = v.findViewById(R.id.plantFoundPictureItem);
        TextView plantFoundName = v.findViewById(R.id.plantFoundName);

        try {
            DataLoader.setImageViewFromPath(plantFound.getPicture(),plantFoundPicture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        plantFoundName.setText(plantFound.getLabel());

        return v;
    }
}
