package edu.polytech.si3.ihm.plantish.find;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
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

import edu.polytech.si3.ihm.plantish.MyLocationListener;
import edu.polytech.si3.ihm.plantish.R;

/**
 * @author Kévin Constantin
 */
public class FindPlantActivity extends AppCompatActivity implements LinkedFilter {
    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);

        org.osmdroid.config.Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        map = findViewById(R.id.mapObject1);
        map.setTileSource(TileSourceFactory.MAPNIK); //Render
        map.setBuiltInZoomControls(true); //Zoomable
        GeoPoint startPoint = new GeoPoint(43.65020, 7.00517); //Données de Marine ici
        IMapController controller = map.getController();
        controller.setZoom(18.0); //Valeur par défault du zoom
        controller.setCenter(startPoint); //Centrer la carte sur ce point

        List<OverlayItem> marqueurs = new ArrayList<>();
        OverlayItem home = new OverlayItem("Point A", "Il s'agit du point A", startPoint);
        Drawable d = home.getMarker(0); //Changer l'image du marqueur, ici un qui existe déjà
        marqueurs.add(home);

        ItemizedOverlayWithFocus<OverlayItem> itemizedOverlayWithFocus = new ItemizedOverlayWithFocus<>(getApplicationContext(), marqueurs, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
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
        findViewById(R.id.findFilterButton).setOnClickListener(v -> {
            startActivity(new Intent(this, FindPlantFilterActivity.class));
        });

        /**
         * Bouton pour centrer la carte sur la position actuelle
         */
        findViewById(R.id.findCenterButton).setOnClickListener(v -> {
            //TODO récupérer la position actuelle avec les données de Marine (= utiliser le startPoint)
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }
}