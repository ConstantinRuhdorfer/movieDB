package de.dhbw.movieDB;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;

public class LogicTest {

    private final MovieDB movieDB = new MovieDB();

    /*
    *   Tests if the recommendation functions returns the correct ammount of recommendations.
     */
    @Test
    void testRecommendationsLimit() {

        List<Movie> lenghtTen = movieDB.getRecommendations(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 10, null);
        List<Movie> lenghtTwenty = movieDB.getRecommendations(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 20, null);
        List<Movie> lenghtFifty = movieDB.getRecommendations(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 50, null);

        assertEquals(10, lenghtTen.size());
        assertEquals(20, lenghtTwenty.size());
        assertEquals(50, lenghtFifty.size());
    }

    /*
    *   Tests if the import was correct.
     */
    @Test
    void testNumberOfImportedMovies() {

        assertEquals(
                9125,
                movieDB.getMovie().size()
        );
    }

    /**
     * Cosider that in the databse are four movies with Matrix in the title, so 4 movies should be returned.
     */
    @Test
    void testSearch() {

        assertEquals(4, movieDB.searchMovies("Matrix").size());
    }

    /**
     * Tests wether extract arguments correctly parses the arguments.
     */
    @Test
    void testExtractArguments() {
        String args[] = {"--test=true", "--limit=\"10\"", "--genre=\"Fantasy\""};

        ArrayList<String> expected = new ArrayList<>();
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
