package de.dhbw.movieDB;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class User {

    private String name;
    private HashMap<Integer, Double> ratedMovie = new HashMap<>();
    private ArrayList<Integer> ratedMovieIDs = new ArrayList<>();

    public User(String name, Double rating, Integer movieId) {
        this.name = name;
        ratedMovie.put(movieId, rating);
        ratedMovieIDs.add(movieId);
    }

    void addRating(Integer movieId, Double rating) {
        ratedMovie.put(movieId, rating);
    }

    void addRated(Integer movieId) { ratedMovieIDs.add(movieId); }

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

    public ArrayList<Integer> getRatedMovieIDs() {
        return ratedMovieIDs;
    }

    public void setRatedMovieIDs(ArrayList<Integer> ratedMovieIDs) {
        this.ratedMovieIDs = ratedMovieIDs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
