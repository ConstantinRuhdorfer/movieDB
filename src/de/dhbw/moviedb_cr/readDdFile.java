package de.dhbw.moviedb_cr;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class readDdFile {

    public static void readFile() throws IOException {

        String in = null;

        try (BufferedReader br = new BufferedReader(new FileReader("movieproject.db.txt"))) {
            while (( in = br.readLine()) != null ) {
                if (in.contains("New_Entity: ")) {
                    String workString = in.substring(12);
                    String[] parts = workString.split(",");

                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
