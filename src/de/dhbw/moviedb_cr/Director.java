package de.dhbw.moviedb_cr;

import java.util.ArrayList;

public class Director {

    private String name;
    private Integer directorID;

    private ArrayList<Integer> movies = new ArrayList<>();

    Director(String name, Integer directorID) {
        this.name = name;
        this.directorID = directorID;
    }

    @Override
    public String toString() {
        return "Director{" +
                "name='" + name + '\'' +
                ", directorID=" + directorID +
                ", movies=" + movies +
                '}';
    }

    void addMovie(Integer moviedId) {
        movies.add(moviedId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDirectorID() {
        return directorID;
    }

    public void setDirectorID(Integer directorID) {
        this.directorID = directorID;
    }

    public ArrayList<Integer> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Integer> movies) {
        this.movies = movies;
    }
}
