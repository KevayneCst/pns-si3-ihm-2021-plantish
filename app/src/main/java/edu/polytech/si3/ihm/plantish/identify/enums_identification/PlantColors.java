package edu.polytech.si3.ihm.plantish.identify.enums_identification;

import edu.polytech.si3.ihm.plantish.identify.Path;

import static edu.polytech.si3.ihm.plantish.identify.Paths.*;
import static edu.polytech.si3.ihm.plantish.identify.Application.LABEL;
import static edu.polytech.si3.ihm.plantish.identify.Application.PATH;

public enum PlantColors {

    BLANC(PATH_TO_BLANC.add(LABEL),null,PATH_TO_BLANC.add(PATH)),
    BLEU(PATH_TO_BLEU.add(LABEL),null,PATH_TO_BLEU.add(PATH)),
    JAUNE(PATH_TO_JAUNE.add(LABEL),null,PATH_TO_JAUNE.add(PATH)),
    MARRON(PATH_TO_MARRON.add(LABEL),null,PATH_TO_MARRON.add(PATH)),
    MAUVE(PATH_TO_MAUVE.add(LABEL),null,PATH_TO_MAUVE.add(PATH)),
    NOIR(PATH_TO_NOIR.add(LABEL),null,PATH_TO_NOIR.add(PATH)),
    ORANGE(PATH_TO_ORANGE.add(LABEL),null,PATH_TO_ORANGE.add(PATH)),
    ROSE(PATH_TO_ROSE.add(LABEL),null,PATH_TO_ROSE.add(PATH)),
    VERT(PATH_TO_VERT.add(LABEL),null,PATH_TO_VERT.add(PATH)),
    MULTICOULEUR(PATH_TO_MULTICOULEUR.add(LABEL),null,PATH_TO_MULTICOULEUR.add(PATH));


    public final Path label;
    public final Path description;
    public final Path picture;

    PlantColors(Path label, Path description, Path picture) {
        this.label = label;
        this.description = description;
        this.picture = picture;
    }
}
