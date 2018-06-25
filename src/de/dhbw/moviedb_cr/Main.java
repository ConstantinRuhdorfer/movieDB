package de.dhbw.moviedb_cr;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            readDdFile.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
