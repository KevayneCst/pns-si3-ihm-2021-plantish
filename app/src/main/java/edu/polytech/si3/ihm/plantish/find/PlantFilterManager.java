package edu.polytech.si3.ihm.plantish.find;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.polytech.si3.ihm.plantish.R;
import edu.polytech.si3.ihm.plantish.plants.Plant;
import edu.polytech.si3.ihm.plantish.posts.Post;
import edu.polytech.si3.ihm.plantish.user.Session;

/**
 * Singleton qui gère les données des plantes du JSON en les filtrant avec le filterData
 *
 * @author Kévin Constantin
 */
public class PlantFilterManager {
    private static PlantFilterManager instance;

    private PlantFilterManager() {
    }

    public List<OverlayItem> getFilteredPlants(Context context, FilterData filter, GeoPoint center) {
        Resources res = context.getResources();
        int sizeListFilter = Integer.parseInt(res.getStringArray(R.array.plants_shown)[filter.getPlantsShownIndex()]);
        int distanceFilter = Integer.parseInt(res.getStringArray(R.array.plants_range)[filter.getPlantsRangeIndex()].replace("m", ""));
        List<Plant> allPlants = Session.getInstance().getPosts().stream().map(Post::getPlant).collect(Collectors.toList());

        //On filtre d'abord par la distance avec le centre
        List<Plant> filteredPlants = allPlants.stream().filter(plant -> center.distanceToAsDouble(plant.getPosition()) <= distanceFilter).collect(Collectors.toList());
        if (filteredPlants.isEmpty()) {
            return new ArrayList<>(); //ça ne sert à rien de continuer, pas de plantes à proximité
        }
        //On garde toutes les plantes filtrées jusqu'au nombre maximum de plantes affichées voulues
        filteredPlants = filteredPlants.subList(0, Math.min(filteredPlants.size(), sizeListFilter));
        List<OverlayItem> items = new ArrayList<>();
        for (Plant p : filteredPlants) {
            items.add(new OverlayItem(p.getTYPE()+" : "+p.getFamily(), p.getDescription(), p.getPosition()));
        }
        return items;
    }

    public static PlantFilterManager getInstance() {
        return instance == null ? new PlantFilterManager() : instance;
    }
}
