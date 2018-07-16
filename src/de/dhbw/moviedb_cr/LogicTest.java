package de.dhbw.moviedb_cr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;

public class LogicTest {

    MovieDB movieDB = new MovieDB();

    @Test
    void testRecommendationsLimit() {

        movieDB.readFile();
        List<Movie> lenghtTen = movieDB.getRecommendations(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), 10);
        List<Movie> lenghtTwenty = movieDB.getRecommendations(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), 20);
        List<Movie> lenghtFifty = movieDB.getRecommendations(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), 50);

        assertEquals(10, lenghtTen.size());
        assertEquals(20, lenghtTwenty.size());
        assertEquals(50, lenghtFifty.size());
    }
}
