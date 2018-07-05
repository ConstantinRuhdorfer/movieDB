package de.dhbw.moviedb_cr;

import java.util.ArrayList;

public class Actor {
    private String name;
    private Integer actorID;
    ArrayList<Integer> movies = new ArrayList<>();

    Actor(String name, Integer actorID) {
        this.name = name;
        this.actorID = actorID;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                ", actorID=" + actorID +
                ", movies=" + movies +
                '}';
    }

    void addMovie(Integer movieId) {
        movies.add(movieId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getActorID() {
        return actorID;
    }

    public void setActorID(Integer actorID) {
        this.actorID = actorID;
    }

    public ArrayList<Integer> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Integer> movies) {
        this.movies = movies;
    }
}
