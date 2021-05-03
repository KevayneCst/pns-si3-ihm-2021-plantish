package edu.polytech.si3.ihm.plantish.identify.lists_identification;

import java.util.ArrayList;

import edu.polytech.si3.ihm.plantish.identify.Criterion;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantSuns.*;


public class ListSun extends ArrayList<Criterion> {
    public ListSun() {
        add(new Criterion(SOLEIL.label, SOLEIL.description, SOLEIL.picture));
        add(new Criterion(MI_OMBRE.label, MI_OMBRE.description, MI_OMBRE.picture));
        add(new Criterion(OMBRE.label, OMBRE.description, OMBRE.picture));
    }
}
