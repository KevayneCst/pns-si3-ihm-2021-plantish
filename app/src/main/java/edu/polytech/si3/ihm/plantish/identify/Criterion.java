package edu.polytech.si3.ihm.plantish.identify;


public class Criterion {
    private Path name;
    private Path description;
    private Path picture;

    public Criterion(Path name, Path description, Path picture) {
        this.name = name;
        this.description = description;
        this.picture=picture;
    }

    public Path getName() {
        return name;
    }
    public Path getDescription() {
        return description;
    }
    public Path getPicture(){ return picture; }

}
