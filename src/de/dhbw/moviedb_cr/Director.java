package de.dhbw.moviedb_cr;

import java.util.ArrayList;

public class Director {

    private String name;
    private Integer directorID;

    ArrayList<Integer> movies = new ArrayList<>();

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

    void addMovie(Integer moviedId) {
        movies.add(moviedId);
    }
}
