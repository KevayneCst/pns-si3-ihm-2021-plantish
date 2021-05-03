package edu.polytech.si3.ihm.plantish.identify.enums_identification;

public enum PlantCriteria {

    TYPE("type"),
    COLOR("color"),
    SEASON("season"),
    SUN("sun"),
    SHAPE("shape"),
    LAYOUT("layout");

    public String label;
    PlantCriteria(String label) {
        this.label = label;
    }
}
