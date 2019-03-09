package de.dhbw.movieDB;

import java.util.ArrayList;

public class Actor {
    private String name;
    private Integer actorID;
    private ArrayList<Integer> moviesId = new ArrayList<>();

    /**
     * Creates an actor object.
     *
     * @param name    The name of the actor.
     * @param actorID The ID of the actor.
     */
    Actor(String name, Integer actorID) {
        this.name = name;
        this.actorID = actorID;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                ", actorID=" + actorID +
                ", moviesId=" + moviesId +
                '}';
    }

    void addMovie(Integer movieId) {
        moviesId.add(movieId);
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

    public ArrayList<Integer> getMoviesId() {
        return moviesId;
    }

    public void setMoviesId(ArrayList<Integer> moviesId) {
        this.moviesId = moviesId;
    }
}
