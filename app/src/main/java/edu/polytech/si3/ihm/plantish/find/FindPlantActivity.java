package edu.polytech.si3.ihm.plantish.find;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

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
        GeoPoint center = MyLocationListener.getLocation(this);
        IMapController controller = map.getController();
        controller.setZoom(18.0); //Valeur par défault du zoom
        controller.setCenter(center); //Centrer la carte sur ce point

        FilterData filter = getIntent().getExtras().getParcelable(KEYWORD_INTENT);
        if (filter == null) {
            filter = FindPlantFilterActivity.defaultData;
        }
        List<OverlayItem> items = PlantFilterManager.getInstance().getFilteredPlants(getApplicationContext(), filter, center);
        String text = "Aucune plante n'a été trouvée avec le filtre actuel";
        if (items.isEmpty()) {
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        }
        OverlayItem home = new OverlayItem("Vous êtes ici", "Votre position actuelle", center);
        Drawable d = home.getMarker(0); //TODO mettre une image selon le type de plante (arbre, fleur...)
        items.add(home);

        ItemizedOverlayWithFocus<OverlayItem> itemizedOverlayWithFocus = new ItemizedOverlayWithFocus<>(getApplicationContext(), items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
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
            controller.setCenter(center);
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