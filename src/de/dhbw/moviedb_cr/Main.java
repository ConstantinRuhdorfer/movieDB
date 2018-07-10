package de.dhbw.moviedb_cr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {

        MovieDB movieDB = new MovieDB();
        ArrayList<String> genreArg = new ArrayList<>();
        ArrayList<String> actorsArg = new ArrayList<>();
        ArrayList<String> filmArg = new ArrayList<>();
        ArrayList<String> directorArg = new ArrayList<>();

        String limitArg = "200";
        Integer limit;

        Boolean test = false;

        try {
            movieDB.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (args.length != 0) {
            for (String s : args) {
                if (s.contains("--genre=")) {
                    genreArg = extractArguments(s);
                } else if (s.contains("--actor=")) {
                    actorsArg = extractArguments(s);
                } else if (s.contains("--director=")) {
                    directorArg = extractArguments(s);
                } else if (s.contains("--film=")) {
                    filmArg = extractArguments(s);
                } else if (s.contains("--limit=")) {
                    limitArg = s.substring(8);
                } else if (s.contains("--test=true")) {

                    test = true;
                    movieDB.runTest();
                }
            }
            if (!test) {
                limit = Integer.parseInt(limitArg);
                movieDB.getRecommendations(actorsArg, filmArg, directorArg, genreArg, limit);
            }
        } else {
            //TODO interaktiver modus
        }
    }

    private static ArrayList<String> extractArguments(String s) {

        return new ArrayList<>(Arrays.asList(s.substring(s.indexOf("=") + 1).split(",")));

    }
}
