package de.dhbw.moviedb_cr;


import java.util.HashMap;

public class User {

    private String name;
    HashMap<Integer, Double> ratedMovie = new HashMap<Integer, Double>();

    User(String name, Double rating, Integer movieId) {
        this.name = name;
        ratedMovie.put(movieId, rating);
    }

    void addRating(Integer movieId, Double rating) {
        ratedMovie.put(movieId, rating);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", ratedMovie=" + ratedMovie +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<Integer, Double> getRatedMovie() {
        return ratedMovie;
    }

    public void setRatedMovie(HashMap<Integer, Double> ratedMovie) {
        this.ratedMovie = ratedMovie;
    }
}
