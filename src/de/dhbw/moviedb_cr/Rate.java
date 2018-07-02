package de.dhbw.moviedb_cr;

public class Rate {

    private String rating;
    private int movieId;

    Rate(
            String rating,
            Integer movieId
    ) {
        this.rating = rating;
        this.movieId = movieId;
    }
}
