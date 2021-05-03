package edu.polytech.si3.ihm.plantish.identify.enums_identification;

import edu.polytech.si3.ihm.plantish.identify.Path;

import static edu.polytech.si3.ihm.plantish.identify.Application.*;
import static edu.polytech.si3.ihm.plantish.identify.Paths.*;

public enum PlantSuns {

    SOLEIL(PATH_TO_SOLEIL.add(LABEL),null,PATH_TO_SOLEIL.add(PATH)),
    MI_OMBRE(PATH_TO_MI_OMBRE.add(LABEL),null,PATH_TO_MI_OMBRE.add(PATH)),
    OMBRE(PATH_TO_OMBRE.add(LABEL),null,PATH_TO_OMBRE.add(PATH));

    public final Path label;
    public final Path description;
    public final Path picture;

    PlantSuns(Path label, Path description, Path picture) {
        this.label = label;
        this.description = description;
        this.picture = picture;
    }
}
