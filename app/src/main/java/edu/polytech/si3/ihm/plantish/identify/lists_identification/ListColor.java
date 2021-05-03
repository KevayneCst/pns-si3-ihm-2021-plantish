package edu.polytech.si3.ihm.plantish.identify.lists_identification;

import java.util.ArrayList;

import edu.polytech.si3.ihm.plantish.identify.Criterion;

import static edu.polytech.si3.ihm.plantish.identify.enums_identification.PlantColors.*;

public class ListColor extends ArrayList<Criterion> {

    public ListColor() {

        add(new Criterion(BLANC.label,BLANC.description,BLANC.picture));
        add(new Criterion(BLEU.label,BLEU.description,BLEU.picture));
        add(new Criterion(JAUNE.label,JAUNE.description,JAUNE.picture));
        add(new Criterion(MARRON.label,MARRON.description,MARRON.picture));
        add(new Criterion(MAUVE.label,MAUVE.description,MAUVE.picture));
        add(new Criterion(NOIR.label,NOIR.description,NOIR.picture));
        add(new Criterion(ORANGE.label,ORANGE.description,ORANGE.picture));
        add(new Criterion(ROSE.label,ROSE.description,ROSE.picture));
        add(new Criterion(VERT.label,VERT.description,VERT.picture));
        add(new Criterion(MULTICOULEUR.label,MULTICOULEUR.description,MULTICOULEUR.picture));

    }
}
