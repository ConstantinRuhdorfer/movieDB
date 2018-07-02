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
}
