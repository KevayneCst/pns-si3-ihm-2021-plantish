package edu.polytech.si3.ihm.plantish;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.polytech.si3.ihm.plantish.plants.Plant;
import edu.polytech.si3.ihm.plantish.posts.Post;
import edu.polytech.si3.ihm.plantish.user.Session;

public class PostPlantActivity extends Fragment {

    Post post;

    private Context ctx ;
    private View view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_plant, container, false);

        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ctx = getActivity();
        view = getView();
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        int pos = bundle.getInt("Position", 0);
        Log.d("aa", "pas de bundle"+pos);
        this.post = Session.getPost(pos);

        TextView family = (TextView) view.findViewById(R.id.family);
        family.setText(post.getPlant().getFamily());

        TextView type = (TextView) view.findViewById(R.id.type);
        type.setText(PlantActivity.englishToFrench(post.getPlant().getTYPE()));

        TextView userText = (TextView) view.findViewById(R.id.user);
        userText.setText("Par "+this.post.getUser().getName());

        TextView date = (TextView) view.findViewById(R.id.date);
        Date date1 = this.post.getDate();
        String[] string = PlantActivity.sdf.format(date1).split("/");
        date.setText("Le "+string[0]+"/"+string[1]+"/"+string[2]);

        TextView description = (TextView) view.findViewById(R.id.description);
        description.setText("\""+post.getPlant().getDescription()+"\"");

        TextView address = (TextView) view.findViewById(R.id.adresse);
        GeoPoint position = this.post.getPlant().getPosition();
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(ctx, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(position.getLatitude(), position.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }

        String addressFound = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        address.setText(addressFound);

        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        image.setImageBitmap(this.post.getImage());

        MapView map = view.findViewById(R.id.mapPlant);
        map.getOverlays().clear();
        map.setTileSource(TileSourceFactory.MAPNIK); //Render
        map.setMultiTouchControls(true);

        GeoPoint startPoint = new GeoPoint(position.getLatitude(), position.getLongitude()); //Données de Marine ici
        IMapController controller = map.getController();
        controller.setZoom(19.2); //Valeur par défault du zoom
        controller.setCenter(startPoint); //Centrer la carte sur ce point

        List<OverlayItem> marqueurs = new ArrayList<>();
        OverlayItem home = new OverlayItem("Votre Plante", "", startPoint);
        Bitmap d =  BitmapFactory.decodeResource(MainActivity.context.getResources(), R.mipmap.grass); //Changer l'image du marqueur, ici un qui existe déjà
        BitmapDrawable dd = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(d, 80, 80, true));
        home.setMarker(dd);
        marqueurs.add(home);

        ItemizedOverlayWithFocus<OverlayItem> itemizedOverlayWithFocus = new ItemizedOverlayWithFocus<>(ctx.getApplicationContext(), marqueurs, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                return true; // Pour qu'il réagisse quand j'ai cliqué dessus
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                return false;
            }
        });


        itemizedOverlayWithFocus.setFocusItemsOnTap(true);
        map.getOverlays().add(itemizedOverlayWithFocus);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    MyPlantsActivity myPlantsActivity = new MyPlantsActivity();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, myPlantsActivity);
                    fragmentTransaction.commit();
                    return true;
                }
                return false;
            }
        });

    }

}
