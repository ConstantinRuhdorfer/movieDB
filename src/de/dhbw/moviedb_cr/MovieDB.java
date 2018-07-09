package de.dhbw.moviedb_cr;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class MovieDB {

    HashMap<Integer, Actor> actor = new HashMap<>();
    HashMap<Integer, Movie> movie = new HashMap<>();
    HashMap<Integer, Director> director = new HashMap<>();
    HashMap<String, User> user = new HashMap<>();

    ArrayList<Movie> searchMovies(String param) {

        ArrayList<Movie> results = new ArrayList<>();

        for (Movie value : movie.values()) {
            if (value.getMovietitle().contains(param)) {
                results.add(value);
            }
        }

        return results;
    }

    void newRate(String name, Double rating, Integer movieId) {
        if (user.get(name) != null) {
            user.get(name).addRating(movieId, rating);
        } else {
            user.put(name, new User(name, rating, movieId));
        }
    }

    @Override
    public String toString() {
        return "MovieDB{" +
                "actor=" + actor +
                ", movie=" + movie +
                ", director=" + director +
                ", user=" + user +
                '}';
    }

    List<Movie> getRecommendations (ArrayList<String> actors, ArrayList<String> films, ArrayList<String> directors, ArrayList<String> genres, Integer limit) {
        Double weight;
        ArrayList<Integer> currentID;
        ArrayList<String> currentName;
        ArrayList<Movie> currentMovies;
        ArrayList<User> currentUser;

        ArrayList<Movie> ratedByOtherUsers = new ArrayList<>();


        for (String _film : films) {
            currentMovies = searchMovies(_film);
            for (Movie _movie : currentMovies) {
                currentUser = _movie.getRatedBy();
                for (User _user : currentUser) {
                    currentID = _user.getRatedMovieIDs();
                    for (Integer id : currentID) {
                        ratedByOtherUsers.add(movie.get(id));
                    }
                }
            }
        }


        for (Movie movie : movie.values()) {

            weight = 1.0;

            for (Movie relatedMovie : ratedByOtherUsers) {
                if (movie.equals(relatedMovie)) {
                    weight = weight * 1.1;
                }
            }


            currentID = movie.getActors();
            for (Integer id : currentID) {
                for (String name : actors) {
                    if (name.equals(actor.get(id).getName())) {
                        weight = weight * 2;
                    }
                }
            }

            currentID = movie.getDirectors();
            for (Integer id : currentID) {
                for (String name : directors) {
                    if (name.equals(director.get(id).getName())) {
                        weight = weight * 2;
                    }
                }
            }

            currentName = movie.getGenreNames();
            for (String genre : currentName) {
                for (String name : genres) {
                    if (name.equals(genre)) {
                        weight = weight * 2;
                    }
                }
            }

            weight = weight * movie.getMovieIMDBRating();

            movie.setCurrentWeight(weight);
        }

        List<Movie> recommendationArray = new ArrayList<>(movie.values());

        recommendationArray = recommendationArray.stream().sorted().limit(limit).collect(Collectors.toList());

        return recommendationArray;
    }

    void readFile() throws IOException {

        String in = null;
        String identifier = null;
        String substrings[];

        Integer id;
        Integer movieId;

        Double rating;

        String name;
        String title;
        String genre;
        String plot;
        String movieReleased;
        String movieIMDBVotes;
        String movieIMDBRating;

        try (BufferedReader br = new BufferedReader(new FileReader("movieproject.db.txt"))) {
            while ((in = br.readLine()) != null) {
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
                            rating = Double.parseDouble("0" + movieIMDBRating);
                            movie.put(id, new Movie(title, plot, movieReleased, movieIMDBVotes, rating, id));
                            movie.get(id).addGenre(genre);
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

                            actor.get(id).addMovie(movieId);
                            movie.get(movieId).addActor(id);

                            break;
                        case ("\"director_id\",\"movie_id\""):
                            substrings = in.split("\",\"");
                            id = Integer.parseInt(substrings[0].substring(1));
                            movieId = Integer.parseInt(substrings[1].trim().substring(0, substrings[1].length() - 1));

                            movie.get(movieId).addDirector(id);
                            director.get(id).addMovie(movieId);

                            break;
                        case ("\"user_name\",\"rating\",\"movie_id\""):
                            substrings = in.split("\",\"");
                            name = substrings[0].substring(1);
                            rating = Double.parseDouble("0" + substrings[1].trim());
                            movieId = Integer.parseInt(substrings[2].trim().substring(0, substrings[2].length() - 1));
                            User newUser = new User(name, rating, movieId);

                            if (user.get(name) != null) {
                                user.get(name).addRating(movieId, rating);
                            } else {
                                user.put(name, newUser);
                                movie.get(movieId).addRatedBy(newUser);
                            }

                            break;
                        default:
                            break;

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void runTest() {
        System.out.println("Some Test");
        // TODO Implement the test
    }

    public HashMap<Integer, Actor> getActor() {
        return actor;
    }

    public void setActor(HashMap<Integer, Actor> actor) {
        this.actor = actor;
    }

    public HashMap<Integer, Movie> getMovie() {
        return movie;
    }

    public void setMovie(HashMap<Integer, Movie> movie) {
        this.movie = movie;
    }

    public HashMap<Integer, Director> getDirector() {
        return director;
    }

    public void setDirector(HashMap<Integer, Director> director) {
        this.director = director;
    }

    public HashMap<String, User> getUser() {
        return user;
    }

    public void setUser(HashMap<String, User> user) {
        this.user = user;
    }
}
