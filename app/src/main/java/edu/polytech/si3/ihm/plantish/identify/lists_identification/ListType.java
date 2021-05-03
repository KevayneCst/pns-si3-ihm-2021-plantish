package edu.polytech.si3.ihm.plantish.identify.lists_identification;

import java.util.ArrayList;

import edu.polytech.si3.ihm.plantish.identify.Criterion;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantTypes.ARBRE;
import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantTypes.BULBE;
import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantTypes.FLEUR;
import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantTypes.GRAMINEE;
import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantTypes.GRASSE;
import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantTypes.HAIE;


public class ListType extends ArrayList<Criterion> {
    public ListType() {
        add(new Criterion(FLEUR.label, FLEUR.description, FLEUR.picture));
        add(new Criterion(GRASSE.label,GRASSE.description,GRASSE.picture));
        add(new Criterion(BULBE.label,BULBE.description,BULBE.picture));
        add(new Criterion(GRAMINEE.label,GRAMINEE.description,GRAMINEE.picture));
        add(new Criterion(HAIE.label,HAIE.description,HAIE.picture));
        add(new Criterion(ARBRE.label,ARBRE.description,ARBRE.picture));
    }


}
