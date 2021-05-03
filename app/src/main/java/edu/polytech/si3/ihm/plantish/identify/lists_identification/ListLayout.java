package edu.polytech.si3.ihm.plantish.identify.lists_identification;

import java.util.ArrayList;

import edu.polytech.si3.ihm.plantish.identify.Criterion;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.LeafLayouts.*;

public class ListLayout extends ArrayList<Criterion> {

    public ListLayout() {

        add(new Criterion(ALTERNES.label,ALTERNES.description,ALTERNES.picture));
        add(new Criterion(OPPOSEES.label,OPPOSEES.description,OPPOSEES.picture));
        add(new Criterion(VERTICILLEES.label,VERTICILLEES.description,VERTICILLEES.picture));
        add(new Criterion(ENGAINANTE.label,ENGAINANTE.description,ENGAINANTE.picture));
        add(new Criterion(ROSETTE.label,ROSETTE.description,ROSETTE.picture));
        add(new Criterion(TOUFFE.label,TOUFFE.description,TOUFFE.picture));

    }
}
