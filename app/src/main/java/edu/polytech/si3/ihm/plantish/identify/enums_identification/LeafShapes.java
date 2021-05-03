package edu.polytech.si3.ihm.plantish.identify.enums_identification;

import edu.polytech.si3.ihm.plantish.identify.Path;

import static edu.polytech.si3.ihm.plantish.identify.Application.*;
import static edu.polytech.si3.ihm.plantish.identify.Paths.*;
import static edu.polytech.si3.ihm.plantish.identify.Paths.PATH_TO_COMPLEXE;
import static edu.polytech.si3.ihm.plantish.identify.Paths.PATH_TO_COMPOSEE;

public enum LeafShapes {

    AIGUILLE(PATH_TO_AIGUILLE.add(LABEL),PATH_TO_AIGUILLE.add(DESCRIPTION),PATH_TO_AIGUILLE.add(PATH)),
    COMPOSEE(PATH_TO_COMPOSEE.add(LABEL),PATH_TO_COMPOSEE.add(DESCRIPTION),PATH_TO_COMPOSEE.add(PATH)),
    LOBEE(PATH_TO_LOBEE.add(LABEL),PATH_TO_LOBEE.add(DESCRIPTION),PATH_TO_LOBEE.add(PATH)),
    SIMPLE(PATH_TO_SIMPLE.add(LABEL),PATH_TO_SIMPLE.add(DESCRIPTION),PATH_TO_SIMPLE.add(PATH)),
    COMPLEXE(PATH_TO_COMPLEXE.add(LABEL),PATH_TO_COMPLEXE.add(DESCRIPTION),PATH_TO_COMPLEXE.add(PATH)),
    SANS_FEUILLE(PATH_TO_SANS_FEUILLE.add(LABEL),PATH_TO_SANS_FEUILLE.add(DESCRIPTION),PATH_TO_SANS_FEUILLE.add(PATH));


    public final Path label;
    public final Path description;
    public final Path picture;

    LeafShapes(Path label, Path description, Path picture) {
        this.label = label;
        this.description = description;
        this.picture = picture;
    }

}
