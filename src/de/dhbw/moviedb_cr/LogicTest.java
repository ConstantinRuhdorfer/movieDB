package de.dhbw.moviedb_cr;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.util.ArrayList;
import java.util.List;

public class LogicTest {

    private final MovieDB movieDB = new MovieDB();

    /*
    *   Wir erwarten, dass das Limit die korrekte Anzahl Filme zurück gibt.
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
    *   Es gibt genau 9125 verschiedene Filme in der File. Diese sollten sich auch in der Datenbank wieder finden.
     */
    @Test
    void testNumberOfImportedMovies() {

        assertEquals(
                9125,
                movieDB.getMovie().size()
        );
    }

    /*
    *   Es gibt vier Filme in denen der Begriff 'matrix' vorkommt:
    *   1. Animatrix, The,
    *   2. Matrix, The
    *   3. Matrix Revolutions
    *   4. Matrix Reloaded
    *
    *   Daher sollte die Länge des zurückgegebenen Arrays vier betragen.
     */
    @Test
    void testSearch() {

        assertEquals(4, movieDB.searchMovies("Matrix").size());
    }

    /*
    *   Die Argumente sollten korrekt von ihren Flags getrennt werden.
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
