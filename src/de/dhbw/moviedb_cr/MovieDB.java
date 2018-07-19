package de.dhbw.moviedb_cr;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("StringConcatenationInsideStringBufferAppend")
public class MovieDB {

    private HashMap<Integer, Actor> actor = new HashMap<>();
    private HashMap<Integer, Movie> movie = new HashMap<>();
    private HashMap<Integer, Director> director = new HashMap<>();
    private HashMap<String, User> user = new HashMap<>();

    MovieDB() {
        readFile();
    }

    /*
    *   Durchsucht die Movie HashMap nach Movietiteln, die im Titel den param enthalten
    *   und gibt alle diese MovieObjekte in einer Arrayliste zurück.
     */
    ArrayList<Movie> searchMovies(String param) {

        ArrayList<Movie> results = new ArrayList<>();

        for (Movie value : movie.values()) {
            if (value.getMovietitle().toLowerCase().contains(param.toLowerCase())) {
                results.add(value);
            }
        }

        return results;
    }

    void newRate(String name, Double rating, Integer movieId) {
        if (user.get(name) != null) {
            if (user.get(name).getRatedMovieIDs().contains(movieId)) {
                user.get(name).addRating(movieId, rating);
            } else {
                user.get(name).addRating(movieId, rating);
                user.get(name).addRated(movieId);
            }
        } else {
            user.put(name, new User(name, rating, movieId));
        }
        writeRating(name, rating, movieId);
    }

    private void writeRating(String name, Double rating, Integer movieId) {
        final String newLine = System.getProperty("line.separator");
        String ratingString = String.format(Locale.US, "%.1f", rating);
        String toWrite = String.format("\"%s\"," + "\"" + ratingString + "\"" + ",\"%d\"", name, movieId);
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(new FileOutputStream("movieproject.db.txt", true));
            printWriter.append(newLine + toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.flush();
                printWriter.close();
            }
        }
    }

    /*
    *   Die getRecommendations Funktion berechenet im wesentlichen für alle Filme ein Gewicht, sortiert alle Filme dann
    *   nach ihrem Gewicht und gibt dann die geforderte Menge an Filmen als Liste wieder zurück.
    *
    *   Zunächst wird das Gewicht von jedem Film auf 1.0 gesetzt.
    *   Um das Gewicht zu berechnen achtet die Funktion auf verschiedene Faktoren (in Reihenfolge der Parameter):
    *  1. Wenn ein Film den gleichen Actor hat, wie in den Parametern angegeben, wird das Gewicht * 2.1 genommen.
    *  2. Von allen übergebenen Filmen werden die User gesucht, die diesen Film gut fanden.
    *     Dann werden die Filme der User gesucht, die sie gut fanden und diesen Filmen wird das Gewicht mit 1.01
    *     multipliziert.
    *  3. Für Directosrs wie bei Actors.
    *  4. Für Genres wie bei Actors.
    *  5. Das Limit bestimmt die Länge der zurückgegebenen Liste.
    *  6. Wird ein User angegeben werden die Filme geholt, die dieser gut fande und dann mit diesen Verfahren, wie unter 2.
    *
    *   Danach wird das so berechnete Gewicht mit dem IMDB Rating des Filmes multipliziert und das Gewicht dem Film hinzugefügt.
    *   Das passiert für jeden Film.
    *   Dann wird sortiert und nach dem Limit zurückgegeben.
     */

    List<Movie> getRecommendations(ArrayList<String> actors, ArrayList<String> films, ArrayList<String> directors, ArrayList<String> genres, Integer limit, String userName) {
        Double weight;
        ArrayList<Integer> currentID;
        ArrayList<String> currentName;
        ArrayList<Movie> currentMovies;

        ArrayList<Movie> likedByOtherUsers = new ArrayList<>();

        User currentUser = user.get(userName);

        for (String _film : films) {
            currentMovies = searchMovies(_film);
            for (Movie _movie : currentMovies) {
                currentName = _movie.getRatedBy();
                for (String name : currentName) {
                    User _user = user.get(name);
                    currentID = _user.getRatedMovieIDs();
                    for (Integer id : currentID) {
                        if (_user.getRatedMovie().get(id) >= 3.5) {
                            likedByOtherUsers.add(movie.get(id));
                        }
                    }
                }
            }
        }


        if (currentUser != null) {
            for (Integer k:  currentUser.getRatedMovie().keySet() ) {
                if ( currentUser.getRatedMovie().get(k) >= 3.5 ) {
                    for (String _userId : movie.get(k).getRatedBy()) {
                        for (Integer _movieId : user.get(_userId).getRatedMovieIDs()) {
                            if( user.get(_userId).getRatedMovie().get(_movieId) >= 3.5 ) {
                                likedByOtherUsers.add(movie.get(_movieId));
                            }
                        }
                    }
                }
            }
        }


        Set<Movie> removeDuplicates = new HashSet<>(likedByOtherUsers);
        likedByOtherUsers.clear();
        likedByOtherUsers.addAll(removeDuplicates);



        for (Movie movie : movie.values()) {

            weight = 1.0;

            for (Movie relatedMovie : likedByOtherUsers) {
                if (movie.equals(relatedMovie)) {
                    weight = weight * 1.01;
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
                        weight = weight * 2.1;
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

    /*
    *   Die Funktion liest die File ein und parst nach den verschiedenen Entities die relevanten Daten in das Datenmodell
    *   und von dort in eine HashMap.
     */
    private void readFile() {

        String in;
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

                            if (movie.get(id) != null) {
                                movie.get(id).addGenre(genre);
                            } else {
                                movie.put(id, new Movie(title, plot, movieReleased, movieIMDBVotes, rating, id));
                            }

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
                                if (user.get(name).getRatedMovieIDs().contains(movieId)) {
                                    user.get(name).addRating(movieId, rating);
                                } else {
                                    user.get(name).addRating(movieId, rating);
                                    user.get(name).addRated(movieId);
                                }
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

        for (User _user : user.values()) {
            ArrayList<Integer> idRatedByUser = _user.getRatedMovieIDs();

            for (Integer _id : idRatedByUser) {
                movie.get(_id).addRatedBy(_user.getName());
            }
        }
    }

    void runTest() {

        System.out.println("Lasse Test laufen und schreibe Output...");
        List<Movie> testRecommendation = new ArrayList<>();

        ArrayList<String> actors = new ArrayList<>();
        ArrayList<String> films = new ArrayList<>(Collections.singletonList("Matrix Revolutions"));
        ArrayList<String> directors = new ArrayList<>();
        ArrayList<String> genre = new ArrayList<>(Collections.singletonList("Thriller"));
        Integer limit = 10;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("test_" + new Date().getTime() + ".txt"))) {
            bw.write("First Recommendation below:\n" +
                    "Params: film = Matrix Revolutions, genre = Thriller, Limit = 10\n");

            testRecommendation = getRecommendations(
                    actors,
                    films,
                    directors,
                    genre,
                    limit,
                    null
            );
            for (Movie movie : testRecommendation) {
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
                    limit,
                    null
            );
            for (Movie movie : testRecommendation) {
                bw.write(movie.toString() + "\n");
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
                    limit,
                    null
            );

            for (Movie movie : testRecommendation) {
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
