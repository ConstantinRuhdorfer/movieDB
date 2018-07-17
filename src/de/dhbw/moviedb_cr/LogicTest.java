package de.dhbw.moviedb_cr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;

public class LogicTest {

    final MovieDB movieDB = new MovieDB();

    @Test
    void testRecommendationsLimit() {

        List<Movie> lenghtTen = movieDB.getRecommendations(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), 10, null);
        List<Movie> lenghtTwenty = movieDB.getRecommendations(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), 20, null);
        List<Movie> lenghtFifty = movieDB.getRecommendations(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), 50, null);

        assertEquals(10, lenghtTen.size());
        assertEquals(20, lenghtTwenty.size());
        assertEquals(50, lenghtFifty.size());
    }

    @Test
    void testNumberOfImportedMovies() {

        assertEquals(
                9125,
                movieDB.getMovie().size()
        );
    }

    @Test
    void testSearch() {

        assertEquals(4, movieDB.searchMovies("Matrix").size());
    }

    @Test
    void testExtractArguments() {
        String args[] = {"--test=true", "--limit=\"10\"", "--genre=\"Fantasy\""};

        ArrayList<String> expected = new ArrayList<String>();
        expected.add("true");
        expected.add("\"10\"");
        expected.add("\"Fantasy\"");

        ArrayList<String> actual = new ArrayList<>();

        for (String arg : args) {
           actual.addAll(Main.extractArguments(arg));
        }

        assertEquals(expected, actual);
    }
}
