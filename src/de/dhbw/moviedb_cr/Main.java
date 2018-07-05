package de.dhbw.moviedb_cr;

import java.io.IOException;
import java.util.Arrays;

public class Main {


    public static void main(String[] args) {

        if (args.length != 0) {
            Arrays.stream(args).forEach(s -> {
                if (s.contains("--genre")) {
                    // TODO Genre Flag found
                } else if (s.contains("--actor=")) {
                    // TODO Actor
                } else if (s.contains("--director=")) {

                } else if (s.contains("--film=")) {

                } else if (s.contains("--limit=")) {

                } else if (s.contains("--test=true")) {
                    /*
                    Abfrage: Empfehlung erhalten für Genre ‘Thriller’ und Film ‘Matrix Revolutions’ mit
                    Limit ’10'
                    2. Abfrage: Empfehlung erhalten für Film ‘Indiana Jones and the Temple of Doom’ und
                    Genre ‘Adventure’ mit Limit ’15'
                    3. Abfrage: Empfehlung erhalten für Schauspieler ‘Jason Statham’ und ‘Keanu Reeves’ und
                    Genre ‘Action’ mit Limit ’50’
                     */
                }
            });
        } else {
            //TODO interaktiver modus
        }
        MovieDB movieDB = new MovieDB();
        try {
            movieDB.readFile();
            movieDB.search("Matrix");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
