package edu.polytech.si3.ihm.plantish.identify;


import edu.polytech.si3.ihm.plantish.R;

public abstract class Paths {

    public static final int PATH_TO_CRITERIA_JSON = R.raw.criteria;
    public static final int PATH_TO_PLANTS_JSON = R.raw.all_plants;

    public static final Path PATH_TO_PLANTE_TYPES = new Path("PlanteTypes");
    public static final Path PATH_TO_FLEUR = PATH_TO_PLANTE_TYPES.add("FLEUR");
    public static final Path PATH_TO_GRASSE = PATH_TO_PLANTE_TYPES.add("GRASSE");
    public static final Path PATH_TO_BULBE= PATH_TO_PLANTE_TYPES.add("BULBE");
    public static final Path PATH_TO_GRAMINEE = PATH_TO_PLANTE_TYPES.add("GRAMINEE");
    public static final Path PATH_TO_HAIE = PATH_TO_PLANTE_TYPES.add("HAIE");
    public static final Path PATH_TO_ARBRE = PATH_TO_PLANTE_TYPES.add("ARBRE");


    public static final Path PATH_TO_PLANTE_COLORS = new Path("PlanteColors");
    public static final Path PATH_TO_BLANC = PATH_TO_PLANTE_COLORS.add("BLANC");
    public static final Path PATH_TO_BLEU = PATH_TO_PLANTE_COLORS.add("BLEU");
    public static final Path PATH_TO_JAUNE = PATH_TO_PLANTE_COLORS.add("JAUNE");
    public static final Path PATH_TO_MARRON = PATH_TO_PLANTE_COLORS.add("MARRON");
    public static final Path PATH_TO_MAUVE = PATH_TO_PLANTE_COLORS.add("MAUVE");
    public static final Path PATH_TO_NOIR = PATH_TO_PLANTE_COLORS.add("NOIR");
    public static final Path PATH_TO_ORANGE = PATH_TO_PLANTE_COLORS.add("ORANGE");
    public static final Path PATH_TO_ROSE = PATH_TO_PLANTE_COLORS.add("ROSE");
    public static final Path PATH_TO_VERT = PATH_TO_PLANTE_COLORS.add("VERT");
    public static final Path PATH_TO_MULTICOULEUR = PATH_TO_PLANTE_COLORS.add("MULTICOULEUR");


    public static final Path PATH_TO_PLANTE_SEASONS = new Path("PlanteSeasons");
    public static final Path PATH_TO_AUTOMNE = PATH_TO_PLANTE_SEASONS.add("AUTOMNE");
    public static final Path PATH_TO_HIVER = PATH_TO_PLANTE_SEASONS.add("HIVER");
    public static final Path PATH_TO_PRINTEMPS = PATH_TO_PLANTE_SEASONS.add("PRINTEMPS");
    public static final Path PATH_TO_ETE = PATH_TO_PLANTE_SEASONS.add("ETE");
    public static final Path PATH_TO_NE_FLEURIT_PAS = PATH_TO_PLANTE_SEASONS.add("NE_FLEURIT_PAS");


    public static final Path PATH_TO_PLANTE_SUNS = new Path("PlanteSuns");
    public static final Path PATH_TO_SOLEIL = PATH_TO_PLANTE_SUNS.add("SOLEIL");
    public static final Path PATH_TO_MI_OMBRE = PATH_TO_PLANTE_SUNS.add("MI_OMBRE");
    public static final Path PATH_TO_OMBRE = PATH_TO_PLANTE_SUNS.add("OMBRE");


    public static final Path PATH_TO_LEAF_SHAPES = new Path("LeafShapes");
    public static final Path PATH_TO_AIGUILLE = PATH_TO_LEAF_SHAPES.add("AIGUILLE");
    public static final Path PATH_TO_COMPOSEE = PATH_TO_LEAF_SHAPES.add("COMPOSEE");
    public static final Path PATH_TO_LOBEE = PATH_TO_LEAF_SHAPES.add("LOBEE");
    public static final Path PATH_TO_SIMPLE = PATH_TO_LEAF_SHAPES.add("SIMPLE");
    public static final Path PATH_TO_COMPLEXE = PATH_TO_LEAF_SHAPES.add("COMPLEXE");
    public static final Path PATH_TO_SANS_FEUILLE = PATH_TO_LEAF_SHAPES.add("SANS_FEUILLE");


    public static final Path PATH_TO_LEAF_LAYOUTS = new Path("LeafLayouts");
    public static final Path PATH_TO_ALTERNES = PATH_TO_LEAF_LAYOUTS.add("ALTERNES");
    public static final Path PATH_TO_OPPOSEES = PATH_TO_LEAF_LAYOUTS.add("OPPOSEES");
    public static final Path PATH_TO_VERTICILLEES = PATH_TO_LEAF_LAYOUTS.add("VERTICILLEES");
    public static final Path PATH_TO_ENGAINANTE = PATH_TO_LEAF_LAYOUTS.add("ENGAINANTE");
    public static final Path PATH_TO_ROSETTE = PATH_TO_LEAF_LAYOUTS.add("ROSETTE");
    public static final Path PATH_TO_TOUFFE = PATH_TO_LEAF_LAYOUTS.add("TOUFFE");

}
