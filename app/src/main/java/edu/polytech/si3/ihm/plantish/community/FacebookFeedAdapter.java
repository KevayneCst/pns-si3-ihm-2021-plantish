package edu.polytech.si3.ihm.plantish.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.polytech.si3.ihm.plantish.R;

public class FacebookFeedAdapter extends RecyclerView.Adapter<FacebookFeedAdapter.ViewHolder> {

    // arraylist for our facebook feeds.
    private ArrayList<FacebookModele> facebookFeedModalArrayList;
    private Context context;
    // creating a constructor for our adapter class.
    public FacebookFeedAdapter(ArrayList<FacebookModele> facebookFeedModalArrayList, Context context) {
        this.facebookFeedModalArrayList = facebookFeedModalArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public FacebookFeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout for item of recycler view item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_facebook_feed, parent, false);
        return new FacebookFeedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacebookFeedAdapter.ViewHolder holder, int position) {
        // getting data from array list and setting it to our modal class.
        FacebookModele modal = facebookFeedModalArrayList.get(position);

        // setting data to each view of recyclerview item.
        Picasso.get().load(modal.getAuthorImage()).into(holder.authorIV);
        holder.name.setText(modal.getName());
        holder.created_time.setText(modal.getCreated_time());
        holder.message.setText(modal.getMessage());
        Picasso.get().load(modal.getPostIV()).into(holder.postIV);
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list.
        return facebookFeedModalArrayList.size();
    }

    public void clear(){
        facebookFeedModalArrayList.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // creating variables for our views
        // of recycler view items.
        private CircleImageView authorIV;
        private TextView name, created_time, message;
        private ImageView postIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our variables
            created_time = itemView.findViewById(R.id.idTVTime);
            message = itemView.findViewById(R.id.idTVDescription);
            authorIV = itemView.findViewById(R.id.idCVAuthor);
            name = itemView.findViewById(R.id.idTVAuthorName);
            postIV = itemView.findViewById(R.id.idIVPost);
        }
    }
}
