package edu.polytech.si3.ihm.plantish.find;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;
import java.util.List;

import edu.polytech.si3.ihm.plantish.MainActivity;
import edu.polytech.si3.ihm.plantish.MyLocationListener;
import edu.polytech.si3.ihm.plantish.R;


/**
 * @author Kévin Constantin
 */
public class FindPlantActivity extends Fragment implements LinkedFilter {
    private MapView map;
    private static final double DEFAULT_ZOOM = 18.0;
    private Context ctx ;
    private View view;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_find, container, false);

        return view;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        ctx = getActivity();
        view = getView();

        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        org.osmdroid.config.Configuration.getInstance().load(ctx.getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(ctx.getApplicationContext()));
        map = view.findViewById(R.id.mapObject1);
        map.setTileSource(TileSourceFactory.MAPNIK); //Render
        map.setMultiTouchControls(true);

        GeoPoint center = MyLocationListener.getLocation(getActivity());
        IMapController controller = map.getController();
        controller.setZoom(DEFAULT_ZOOM); //Valeur par défault du zoom
        controller.setCenter(center); //Centrer la carte sur ce point

        FilterData filter = FindPlantFilterActivity.defaultData;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            FilterData parcelable = bundle.getParcelable(KEYWORD_INTENT);
            if (parcelable != null) {
                filter = parcelable;
            }
        }

        List<OverlayItem> items = PlantFilterManager.getInstance().getFilteredPlants(ctx.getApplicationContext(), filter, center);
        OverlayItem home = new OverlayItem("Vous êtes ici", "Votre position actuelle", center);
        items.add(home);

        ItemizedOverlayWithFocus<OverlayItem> itemizedOverlayWithFocus = new ItemizedOverlayWithFocus<>(ctx.getApplicationContext(), items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
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

        /**
         * Bouton pour acceder à la page de filtre
         */
        view.findViewById(R.id.findFilterButton).setOnClickListener(v -> {
            FindPlantFilterActivity filterFragment = new FindPlantFilterActivity();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, filterFragment);
            fragmentTransaction.commit();
        });

        /**
         * Bouton pour centrer la carte sur la position actuelle
         */
        view.findViewById(R.id.findCenterButton).setOnClickListener(v -> {
            controller.setCenter(center);
            controller.setZoom(DEFAULT_ZOOM);
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }
}