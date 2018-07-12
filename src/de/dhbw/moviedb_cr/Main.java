package de.dhbw.moviedb_cr;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        MovieDB movieDB = new MovieDB();
        ArrayList<String> genreArg = new ArrayList<>();
        ArrayList<String> actorsArg = new ArrayList<>();
        ArrayList<String> filmArg = new ArrayList<>();
        ArrayList<String> directorArg = new ArrayList<>();

        ArrayList<User> movieDBUsers = new ArrayList<>();

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
                movieDB.getRecommendations(
                        actorsArg,
                        filmArg,
                        directorArg,
                        genreArg,
                        limit
                );
            }
        } else {
            startInteractive(movieDBUsers, movieDB);
        }
    }

    private static void startInteractive(ArrayList<User> movieDBUsers, MovieDB movieDB) {

        ArrayList<String> actor;
        ArrayList<String> director;
        ArrayList<String> film;
        ArrayList<String> genres;

        ArrayList<Integer> id;

        Integer limit;

        List<Movie> recommendations;

        User currentUser = new User("Default");
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {

                System.out.println("Willkommen zur MovieDB! Was möchtest du tuen?");
                System.out.println("[1] Benutzermanagement");
                System.out.println("[2] Nach einem Film suchen.");
                System.out.println("[3] Filmempfehlungen erhalten.");
                System.out.println("[4] Einen Film bewerten.");
                System.out.println("[0] Das Programm beenden.");

                String line = scanner.nextLine();

                switch (line) {
                    case "1":
                        System.out.println("Willkommen zum Benutzermanagment.");
                        System.out.println("[1] Neuer Nutzer anlegen.");
                        System.out.println("[2] Alten Nutzer laden.");

                        line = scanner.nextLine();

                        switch (line) {
                            case "1":
                                System.out.println("Bitte gebe einen neuen Namen ein.");
                                line = scanner.nextLine();
                                movieDBUsers.add(new User(line));
                                break;
                            case "2":
                                System.out.println("Bitte gebe den Namen des alten Users an.");
                                line = scanner.nextLine();
                                for (User user : movieDBUsers) {
                                    if (user.getName().equals(line)) {
                                        currentUser = user;
                                    }
                                }
                                break;
                            default:
                                System.out.println("Der Input wurde leider nicht erkannt.");
                                break;
                        }
                    case "2":
                        System.out.println("Nach welchem Film suchst du?");
                        break;
                    case "3":
                        System.out.println("Schön! Lass uns mal sehen...");
                        System.out.println("Du kannst mehrere Angaben getrennt durch ein Komma machen.");
                        System.out.println("Bsp.: Tom Hanks,Max Mustermann");
                        System.out.println("Welche Schauspieler magst du?");
                        line = scanner.nextLine();
                        actor = extractArguments(line);
                        System.out.println("Gibt es Filme die wir besonders berücksichtigen sollen?");
                        line = scanner.nextLine();
                        film = extractArguments(line);
                        System.out.println("Welche Genres schaust du gerne?");
                        line = scanner.nextLine();
                        genres = extractArguments(line);
                        System.out.println("Welche Direktoren gefallen dir?");
                        line = scanner.nextLine();
                        director = extractArguments(line);
                        System.out.println("Wie viele Empfehlungen möchtest du bekommen?");
                        line = scanner.nextLine();
                        limit = Integer.parseInt(line);

                        System.out.println("Magie...");
                        recommendations = movieDB.getRecommendations(actor, film, director, genres, limit);
                        System.out.println("...und peng!");
                        System.out.println("Möchtest du nur Filmtitel oder auch mehr Details?");
                        System.out.println("[1] Filmtitel.");
                        System.out.println("[2] Details.");

                        line = scanner.nextLine();
                        switch (line) {
                            case "1":
                                for (Movie movie : recommendations) {
                                    System.out.println(movie.getMovietitle());
                                }
                                break;
                            case "2":
                                for (Movie movie : recommendations) {
                                    System.out.println(movie.getMovietitle());
                                    System.out.println("Plot:");
                                    System.out.println(movie.getMoviePlot());
                                    id = movie.getActors();
                                    System.out.println("Schauspieler:");
                                    for (Integer actorId : id) {
                                        System.out.println(movieDB.getActor().get(actorId).getName());
                                    }
                                    id = movie.getDirectors();
                                    System.out.println("Direktoren:");
                                    for (Integer directorId: id) {
                                        System.out.println(movieDB.getDirector().get(directorId).getName());
                                    }
                                }
                                break;
                            default:
                                System.out.println("Leider wurde der Input nicht verstanden.");
                                break;
                        }
                        break;
                    case "4":
                        Double rating;

                        System.out.println("Welchen Film willst du bewerten?");
                        line = scanner.nextLine();
                         recommendations = movieDB.searchMovies(line);
                        if ( recommendations.size() == 1 ) {
                            System.out.println("Gib dem Film zwischen 1 und 5 Sternen:");
                            line = scanner.nextLine();
                            rating = Double.parseDouble(line);

                            // TODO DO SOMETHING WITH RATING
                        } else {
                            // TODO NEEDS CLARIFICATION
                        }
                        break;
                    default:
                        break;
                }
            }
        } catch (IllegalStateException | NoSuchElementException e) {
            System.out.println("System.in wurde beendet. Fahre herunter.");
        }
    }

    private static ArrayList<String> extractArguments(String s) {

        return new ArrayList<>(
                Arrays.asList(
                        s.substring(
                                s.indexOf("=") + 1)
                                .split(",")
                ));

    }
}
