package edu.polytech.si3.ihm.plantish.identify.enums_identification;

import edu.polytech.si3.ihm.plantish.identify.Path;

import static edu.polytech.si3.ihm.plantish.identify.Paths.*;
import static edu.polytech.si3.ihm.plantish.identify.Application.*;

public enum PlantTypes {


    FLEUR(PATH_TO_FLEUR.add(LABEL), PATH_TO_FLEUR.add(DESCRIPTION), PATH_TO_FLEUR.add(PATH)),
    GRASSE(PATH_TO_GRASSE.add(LABEL), PATH_TO_GRASSE.add(DESCRIPTION), PATH_TO_GRASSE.add(PATH)),
    BULBE(PATH_TO_BULBE.add(LABEL), PATH_TO_BULBE.add(DESCRIPTION), PATH_TO_BULBE.add(PATH) ),
    GRAMINEE(PATH_TO_GRAMINEE.add(LABEL), PATH_TO_GRAMINEE.add(DESCRIPTION), PATH_TO_GRAMINEE.add(PATH)),
    HAIE(PATH_TO_HAIE.add(LABEL), PATH_TO_HAIE.add(DESCRIPTION), PATH_TO_HAIE.add(PATH)),
    ARBRE(PATH_TO_ARBRE.add(LABEL), PATH_TO_ARBRE.add(DESCRIPTION),  PATH_TO_ARBRE.add(PATH));

    public final Path label;
    public final Path description;
    public final Path picture;

    PlantTypes(Path label, Path description, Path picture) {
        this.label = label;
        this.description = description;
        this.picture = picture;
    }
}
