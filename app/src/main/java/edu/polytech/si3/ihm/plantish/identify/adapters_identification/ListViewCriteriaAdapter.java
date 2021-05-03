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
import edu.polytech.si3.ihm.plantish.identify.Criterion;
import edu.polytech.si3.ihm.plantish.identify.DataLoader;

public class ListViewCriteriaAdapter extends ArrayAdapter<Criterion> {

    private Context context;

    public ListViewCriteriaAdapter(Context context, int resource, List<Criterion> objects) {
        super(context, resource, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.criterion_list_item, null);
        }
        Criterion criterion = getItem(position);
        ImageView criterionImage = v.findViewById(R.id.criterionPicture);
        TextView criterionName = v.findViewById(R.id.criterionName);
        TextView criterionDescription = v.findViewById(R.id.criterionDescription);

        try {
            DataLoader.setImageViewForCriterion(context, criterion.getPicture(), criterionImage);
            DataLoader.setCriterionInfoTextView(context, criterion.getName(), criterionName);
            DataLoader.setCriterionInfoTextView(context, criterion.getDescription(), criterionDescription);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }
}
