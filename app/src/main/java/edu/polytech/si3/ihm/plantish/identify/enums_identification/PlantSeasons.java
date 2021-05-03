package edu.polytech.si3.ihm.plantish.identify.enums_identification;

import edu.polytech.si3.ihm.plantish.identify.Path;

import static edu.polytech.si3.ihm.plantish.identify.Application.*;
import static edu.polytech.si3.ihm.plantish.identify.Paths.*;

public enum PlantSeasons {


    AUTOMNE(PATH_TO_AUTOMNE.add(LABEL),null,PATH_TO_AUTOMNE.add(PATH)),
    HIVER(PATH_TO_HIVER.add(LABEL),null,PATH_TO_HIVER.add(PATH)),
    PRINTEMPS(PATH_TO_PRINTEMPS.add(LABEL),null,PATH_TO_PRINTEMPS.add(PATH)),
    ETE(PATH_TO_ETE.add(LABEL),null,PATH_TO_ETE.add(PATH)),
    NE_FLEURIT_PAS(PATH_TO_NE_FLEURIT_PAS.add(LABEL),null,PATH_TO_NE_FLEURIT_PAS.add(PATH));

    public final Path label;
    public final Path description;
    public final Path picture;

    PlantSeasons(Path label, Path description, Path picture) {
        this.label = label;
        this.description = description;
        this.picture = picture;
    }
}
