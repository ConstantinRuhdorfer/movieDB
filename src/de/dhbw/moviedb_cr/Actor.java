package de.dhbw.moviedb_cr;

public class Actor {
    private String name;

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

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                ", actorID=" + actorID +
                '}';
    }

    private Integer actorID;

    Actor(String name, Integer actorID) {
        this.name = name;
        this.actorID = actorID;
    }
}
