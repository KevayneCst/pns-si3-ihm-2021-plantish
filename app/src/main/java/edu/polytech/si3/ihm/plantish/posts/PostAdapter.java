package edu.polytech.si3.ihm.plantish.posts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.user.Session;

public class PostAdapter extends ArrayAdapter {
    private final Context context;
    private List<Post> posts;

    public PostAdapter(Context context, List<Post> posts){
        super(context, R.layout.row_item, posts);
        Log.i("POSTADAPTER", posts.toString());
        this.posts = posts;
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.row_item, parent, false);


        TextView textViewCountry = (TextView) row.findViewById(R.id.textViewCountry);
        TextView textViewCapital = (TextView) row.findViewById(R.id.textViewCapital);
        ImageView imageFlag = (ImageView) row.findViewById(R.id.imageViewFlag);
        TextView textViewDescription = (TextView) row.findViewById(R.id.textViewDescription);

        textViewCountry.setText(posts.get(position).plant.getFamily());
        textViewCapital.setText("by " + posts.get(position).user.getName());
        imageFlag.setImageBitmap(posts.get(position).image);
        String description = posts.get(position).plant.getDescription();
        if(description.length() > 35){
            description = description.substring(0,35)+"...";
        }

        textViewDescription.setText(description);

        Button deleteBtn = (Button) row.findViewById(R.id.buttonDelete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle("Supression du post");
                builder.setMessage("Voulez-vous vraiment supprimer ce post ?");
                builder.setPositiveButton("Confirmer",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Session.deletePost(posts.get(position));
                                notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        Log.i("POSTADAPTER", posts.get(position).plant.getFamily());

        return  row;
    }
}
