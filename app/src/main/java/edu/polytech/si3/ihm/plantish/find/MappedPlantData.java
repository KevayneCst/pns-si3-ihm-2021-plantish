package edu.polytech.si3.ihm.plantish.find;

/**
 * Classe reprenant le pattern singleton. Elle gère les données liées aux plantes affichées sur la carte globale
 *
 * @author Kévin Constantin
 */
public class MappedPlantData {
    private MappedPlantData singleton;

    private MappedPlantData() {

    }

    public MappedPlantData getSingleton() {
        return singleton;
    }
}
