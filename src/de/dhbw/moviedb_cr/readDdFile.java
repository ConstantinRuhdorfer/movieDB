package de.dhbw.moviedb_cr;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class readDdFile {

    public static void readFile() throws IOException {

        String in = null;
        String identifier = null;

        try (BufferedReader br = new BufferedReader(new FileReader("movieproject.db.txt"))) {
            while (( in = br.readLine()) != null ) {
                if (in.contains("New_Entity: ")) {
                    identifier = in.substring(12);
                } else {
                    switch (identifier) {
                        case ("\"actor_id\",\"actor_name\""):
                            System.out.println("Actor!");
                            break;
                        case ("\"movie_id\",\"movie_title\",\"movie_plot\",\"genre_name\",\"movie_released\",\"movie_imdbVotes\",\"movie_imdbRating\""):
                            System.out.println("Movie!");
                            break;
                        case ("\"director_id\",\"director_name\""):
                            System.out.println("Director!");
                            break;
                        case ("\"actor_id\",\"movie_id\""):
                            break;
                        case ("\"director_id\",\"movie_id\""):
                            break;
                        case ("\"user_name\",\"rating\",\"movie_id\""):
                            break;
                        default:
                            break;

                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
