package edu.polytech.si3.ihm.plantish.identify.lists_identification;

import java.util.ArrayList;

import edu.polytech.si3.ihm.plantish.identify.Criterion;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantSeasons.*;


public class ListSeason extends ArrayList<Criterion> {

    public ListSeason(){
        add(new Criterion(AUTOMNE.label, AUTOMNE.description, AUTOMNE.picture));
        add(new Criterion(HIVER.label, HIVER.description, HIVER.picture));
        add(new Criterion(PRINTEMPS.label, PRINTEMPS.description, PRINTEMPS.picture));
        add(new Criterion(ETE.label, ETE.description, ETE.picture));
        add(new Criterion(NE_FLEURIT_PAS.label, NE_FLEURIT_PAS.description, NE_FLEURIT_PAS.picture));
    }
}
