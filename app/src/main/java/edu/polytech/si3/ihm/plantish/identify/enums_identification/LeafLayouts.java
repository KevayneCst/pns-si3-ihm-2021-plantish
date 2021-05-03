package edu.polytech.si3.ihm.plantish.identify.enums_identification;

import edu.polytech.si3.ihm.plantish.identify.Path;

import static edu.polytech.si3.ihm.plantish.identify.Application.*;
import static edu.polytech.si3.ihm.plantish.identify.Paths.*;
import static edu.polytech.si3.ihm.plantish.identify.Paths.PATH_TO_OPPOSEES;

public enum LeafLayouts {

    ALTERNES(PATH_TO_ALTERNES.add(LABEL),PATH_TO_ALTERNES.add(DESCRIPTION),PATH_TO_ALTERNES.add(PATH)),
    OPPOSEES(PATH_TO_OPPOSEES.add(LABEL),PATH_TO_OPPOSEES.add(DESCRIPTION),PATH_TO_OPPOSEES.add(PATH)),
    VERTICILLEES(PATH_TO_VERTICILLEES.add(LABEL),PATH_TO_VERTICILLEES.add(DESCRIPTION),PATH_TO_VERTICILLEES.add(PATH)),
    ENGAINANTE(PATH_TO_ENGAINANTE.add(LABEL),PATH_TO_ENGAINANTE.add(DESCRIPTION),PATH_TO_ENGAINANTE.add(PATH)),
    ROSETTE(PATH_TO_ROSETTE.add(LABEL),PATH_TO_ROSETTE.add(DESCRIPTION),PATH_TO_ROSETTE.add(PATH)),
    TOUFFE(PATH_TO_TOUFFE.add(LABEL),PATH_TO_TOUFFE.add(DESCRIPTION),PATH_TO_TOUFFE.add(PATH));

    public final Path label;
    public final Path description;
    public final Path picture;

    LeafLayouts(Path label, Path description, Path picture) {
        this.label = label;
        this.description = description;
        this.picture = picture;
    }

}
