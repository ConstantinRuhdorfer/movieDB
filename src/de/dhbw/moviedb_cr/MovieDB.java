package de.dhbw.moviedb_cr;


import java.io.*;
import java.util.*;
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


    List<Movie> getRecommendations(ArrayList<String> actors, ArrayList<String> films, ArrayList<String> directors, ArrayList<String> genres, Integer limit) {

        Double weight;
        ArrayList<Integer> currentID;
        ArrayList<String> currentName;
        ArrayList<Movie> currentMovies;

        ArrayList<Movie> ratedByOtherUsers = new ArrayList<>();

        for (String _film : films) {
            currentMovies = searchMovies(_film);
            for (Movie _movie : currentMovies) {
                currentName = _movie.getRatedBy();
                for (String name : currentName) {
                    User _user = user.get(name);
                    currentID = _user.getRatedMovieIDs();
                    for (Integer id : currentID) {
                        if (_user.getRatedMovie().get(id) > 5.0 ) {
                            ratedByOtherUsers.add(movie.get(id));
                        }
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
                    if (actor.get(id).getName().contains(name)) {
                        weight = weight * 2.1;
                    }
                }
            }

            currentID = movie.getDirectors();
            for (Integer id : currentID) {
                for (String name : directors) {
                    if (director.get(id).getName().contains(name)) {
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
                                user.get(name).addRated(movieId);
                            } else {
                                user.put(name, newUser);
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

        // TODO Broken

        for(User _user: user.values()) {
            ArrayList<Integer> idRatedByUser = _user.getRatedMovieIDs();

            for (Integer _id: idRatedByUser) {
                movie.get(_id).addRatedBy(_user.getName());
            }
        }
    }

    void runTest() throws IOException {

        System.out.println("Running Test");
        List<Movie> testRecommendation = new ArrayList<>();

        ArrayList<String> actors = new ArrayList<>();
        ArrayList<String> films = new ArrayList<String>(Arrays.asList("Matrix Revolutions"));
        ArrayList<String> directors = new ArrayList<>();
        ArrayList<String> genre = new ArrayList<String>(Arrays.asList("Thriller"));
        Integer limit = 10;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("test_" + new Date().getTime() + ".txt"));
        ) {
            bw.write("First Recommendation below:\n" +
                    "Params: film = Matrix Revolutions, genre = Thriller, Limit = 10\n");

            testRecommendation = getRecommendations(
                    actors,
                    films,
                    directors,
                    genre,
                    limit
            );
            for(Movie movie: testRecommendation) {
                bw.write(movie.toString() + "\n");
            }

            bw.write("\nSecond Recommendation below:\n" +
                    "Params: film = Indiana Jones and the Temple of Doom , genre = Adventure, Limit = 15\n");

            films = new ArrayList<>(Collections.singletonList("Indiana Jones and the Temple of Doom"));
            genre = new ArrayList<>(Collections.singletonList("Adventure"));
            limit = 15;

            testRecommendation = getRecommendations(
                    actors,
                    films,
                    directors,
                    genre,
                    limit
            );
            for(Movie movie: testRecommendation) {
                bw.write(movie.toString()+ "\n");
            }

            bw.write("\nThird Recommendation below:" +
                    "\nParams: actors = Jason Statham + Keanu Reeves , genre = Action, Limit = 50\n");

            actors = new ArrayList<>(Arrays.asList("Jason Statham", "Keanu Reeves"));
            films = new ArrayList<>(Collections.emptyList());
            genre = new ArrayList<>(Collections.singletonList("Action"));
            limit = 50;

            testRecommendation = getRecommendations(
                    actors,
                    films,
                    directors,
                    genre,
                    limit
            );

            for(Movie movie: testRecommendation) {
                bw.write(movie.toString() + "\n");
            }

            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
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
