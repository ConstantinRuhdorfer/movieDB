package de.dhbw.moviedb_cr;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        MovieDB movieDB = new MovieDB();
        ArrayList<String> genreArg = new ArrayList<>();
        ArrayList<String> actorsArg = new ArrayList<>();
        ArrayList<String> filmArg = new ArrayList<>();
        ArrayList<String> directorArg = new ArrayList<>();

        ArrayList<User> movieDBUsers = new ArrayList<>();

        String limitArg = "200";
        Integer limit;

        Boolean test = false;


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
                List<Movie> recommendations = movieDB.getRecommendations(
                        actorsArg,
                        filmArg,
                        directorArg,
                        genreArg,
                        limit,
                        null
                );
                recommendations.forEach(movie -> {
                    System.out.println(movie.getMovietitle());
                });
            }
        } else {
            startInteractive(movieDBUsers, movieDB);
        }
    }

    private static void startInteractive(ArrayList<User> movieDBUsers, MovieDB movieDB) {

        ArrayList<String> actor = new ArrayList<>();
        ArrayList<String> director = new ArrayList<>();
        ArrayList<String> film = new ArrayList<>();
        ArrayList<String> genres = new ArrayList<>();

        ArrayList<Integer> id;

        Integer limit;

        Boolean stop = false;

        List<Movie> recommendations;

        String userName = "DefaultUser";

        Scanner scanner = new Scanner(System.in);
        try {
            while (!stop) {

                System.out.println("---------------------------------------------------------------");
                System.out.println("Willkommen zur MovieDB, " + userName + "! Was möchtest du tuen?");
                System.out.println("[1] Namen eingeben.");
                System.out.println("[2] Nach einem Film suchen.");
                System.out.println("[3] Filmempfehlungen erhalten.");
                System.out.println("[4] Einen Film bewerten.");
                System.out.println("[0] Das Programm beenden.");

                String line = scanner.nextLine();

                switch (line) {
                    case "1":
                        System.out.println("Bitte gebe einen neuen Namen ein.");
                        line = scanner.nextLine();
                        userName = line;
                        break;
                    case "2":
                        System.out.println("Nach welchem Film suchst du?");
                        line = scanner.nextLine();
                        recommendations = movieDB.searchMovies(line);
                        System.out.println("Gefunden haben wir:");
                        for (Movie movie : recommendations) {
                            System.out.println(movie.getMovietitle());
                            System.out.println(movie.toString());
                        }
                        break;
                    case "3":
                        System.out.println("Schön! Lass uns mal sehen...");
                        System.out.println("Du kannst mehrere Angaben getrennt durch ein Komma machen.");
                        System.out.println("Bsp.: Tom Hanks,Max Mustermann");
                        System.out.println("Welche Schauspieler magst du?");
                        line = scanner.nextLine();
                        if (!line.isEmpty()) {
                            actor = extractArguments(line);
                        }
                        System.out.println("Gibt es Filme die wir besonders berücksichtigen sollen?");
                        line = scanner.nextLine();
                        if (!line.isEmpty()) {
                            film = extractArguments(line);
                        }
                        System.out.println("Welche Genres schaust du gerne?");
                        line = scanner.nextLine();
                        if (!line.isEmpty()) {
                            genres = extractArguments(line);
                        }
                        System.out.println("Welche Direktoren gefallen dir?");
                        line = scanner.nextLine();
                        if (!line.isEmpty()) {
                            director = extractArguments(line);
                        }

                        System.out.println("Wie viele Empfehlungen möchtest du bekommen?");
                        line = scanner.nextLine();
                        limit = Integer.parseInt(line);

                        System.out.println("Magie...");
                        recommendations = movieDB.getRecommendations(actor, film, director, genres, limit, userName);
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
                                    for (Integer directorId : id) {
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
                        if (recommendations.size() == 1) {
                            System.out.println("Gib dem Film zwischen 1 und 5 Sternen:");
                            line = scanner.nextLine();
                            rating = Double.parseDouble(line.replaceAll(",","."));
                            Integer _id = recommendations.get(0).getMovieID();
                            movieDB.newRate(userName, rating, _id);
                        } else {
                            System.out.println("Sieht aus als hätten wir mehr als einen Film gefunden!");
                            Integer i = 0;
                            for (Movie movie : recommendations) {
                                System.out.println("[" + i + "] " + movie.getMovietitle());
                                i++;
                            }
                            System.out.println("Welchen Film davon meinst du? Benutz einfach seine Nummer!");
                            line = scanner.nextLine();
                            if ( recommendations.size() >= (Integer.parseInt(line) + 1)) {
                                Movie choosenMovie = recommendations.get(Integer.parseInt(line));
                                System.out.println("Du hast dich für " + choosenMovie.getMovietitle() + " entschieden.");
                                Integer _id = choosenMovie.getMovieID();
                                System.out.println("Gib dem Film zwischen 1 und 5 Sternen:");
                                line = scanner.nextLine();
                                rating = Double.parseDouble(line);
                                if ((rating >= 1.0) && (rating <= 5.0)) {
                                    movieDB.newRate(userName, rating, _id);
                                } else {
                                    System.out.println("Fehler!\nDeine Bewertung muss zwischen 1 und 5 Sternen liegen.");
                                }
                            } else {
                                System.out.println("Fehler!\nDeine Zahl hat keiner der angebotenen Zahlen entsprochen.");
                            }
                        }
                        break;
                    case "0":
                        stop = true;
                        break;
                    default:
                        System.out.println("Dein Befehl wurde leider nicht erkannt.");
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
