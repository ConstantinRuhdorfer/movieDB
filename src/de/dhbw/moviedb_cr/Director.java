package de.dhbw.moviedb_cr;

public class Director {

    private String name;
    private Integer directorID;

    @Override
    public String toString() {
        return "Director{" +
                "name='" + name + '\'' +
                ", directorID=" + directorID +
                '}';
    }

    Director(String name, Integer directorID) {
        this.name = name;
        this.directorID = directorID;
    }
}
