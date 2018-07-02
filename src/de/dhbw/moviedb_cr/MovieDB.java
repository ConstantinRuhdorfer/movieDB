package de.dhbw.moviedb_cr;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MovieDB {

    HashMap<Integer, Actor> actor = new HashMap<>();
    HashMap<Integer, Movie> movie = new HashMap<>();
    HashMap<Integer, Director> director = new HashMap<>();

    HashMap<Integer, Integer> actorToMovie = new HashMap<>();
    HashMap<Integer, Integer> movieToActor = new HashMap<>();

    HashMap<Integer, Integer> directorToMovie = new HashMap<>();
    HashMap<Integer, Integer> movieToDirector = new HashMap<>();

    public void readFile() throws IOException {

        String in = null;
        String identifier = null;
        String substrings[];

        Integer id;
        Integer movieId;

        String name;
        String title;
        String genre;
        String plot;
        String movieReleased;
        String movieIMDBVotes;
        String movieIMDBRating;

        try (BufferedReader br = new BufferedReader(new FileReader("movieproject.db.txt"))) {
            while (( in = br.readLine()) != null ) {
                if (in.contains("New_Entity: ")) {
                    identifier = in.substring(12);
                } else {
                    switch (identifier) {
                        case ("\"actor_id\",\"actor_name\""):
                            substrings = in.split("\",\"");
                            id = Integer.parseInt(substrings[0].substring(1));
                            name = substrings[1].trim().substring(0, substrings[1].length() - 1);
                            actor.put(id, new Actor(name, id));
                            break;
                        case ("\"movie_id\",\"movie_title\",\"movie_plot\",\"genre_name\",\"movie_released\",\"movie_imdbVotes\",\"movie_imdbRating\""):
                            substrings = in.split("\",\"");
                            id = Integer.parseInt(substrings[0].substring(1));
                            title = substrings[1].trim();
                            plot = substrings[2].trim();
                            genre = substrings[3].trim();
                            movieReleased = substrings[4].trim();
                            movieIMDBVotes = substrings[5].trim();
                            movieIMDBRating = substrings[6].trim().substring(0, substrings[6].length() - 1);
                            movie.put(id, new Movie(title, plot, genre, movieReleased, movieIMDBVotes, movieIMDBRating, id));
                            break;
                        case ("\"director_id\",\"director_name\""):
                            substrings = in.split("\",\"");
                            id = Integer.parseInt(substrings[0].substring(1));
                            name = substrings[1].trim().substring(0, substrings[1].length() - 1);
                            director.put(id, new Director(name, id));
                            break;
                        case ("\"actor_id\",\"movie_id\""):
                            substrings = in.split("\",\"");
                            id = Integer.parseInt(substrings[0].substring(1));
                            movieId = Integer.parseInt(substrings[1].trim().substring(0, substrings[1].length() - 1));
                            actorToMovie.put(id, movieId);
                            movieToActor.put(movieId, id);
                            break;
                        case ("\"director_id\",\"movie_id\""):
                            substrings = in.split("\",\"");
                            id = Integer.parseInt(substrings[0].substring(1));
                            movieId = Integer.parseInt(substrings[1].trim().substring(0, substrings[1].length() - 1));
                            directorToMovie.put(id, movieId);
                            movieToDirector.put(movieId, id);
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
