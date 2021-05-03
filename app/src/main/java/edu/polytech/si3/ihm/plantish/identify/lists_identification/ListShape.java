package edu.polytech.si3.ihm.plantish.identify.lists_identification;

import java.util.ArrayList;

import edu.polytech.si3.ihm.plantish.identify.Criterion;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.LeafShapes.*;

public class ListShape extends ArrayList<Criterion> {

    public ListShape() {
        add(new Criterion(AIGUILLE.label, AIGUILLE.description, AIGUILLE.picture));
        add(new Criterion(COMPOSEE.label, COMPOSEE.description, COMPOSEE.picture));
        add(new Criterion(LOBEE.label, LOBEE.description, LOBEE.picture));
        add(new Criterion(SIMPLE.label, SIMPLE.description, SIMPLE.picture));
        add(new Criterion(COMPLEXE.label, COMPLEXE.description, COMPLEXE.picture));
        add(new Criterion(SANS_FEUILLE.label, SANS_FEUILLE.description, SANS_FEUILLE.picture));
    }
}
