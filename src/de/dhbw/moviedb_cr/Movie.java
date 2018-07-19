package de.dhbw.moviedb_cr;

import java.util.ArrayList;

public class Movie implements Comparable {

    private String movietitle;
    private String moviePlot;
    private String movieReleased;
    private String movieIMDBVotes;
    private Double movieIMDBRating;
    private Integer movieID;

    private Double currentWeight;

    private ArrayList<String> genreNames = new ArrayList<>();
    private ArrayList<String> ratedBy = new ArrayList<>();
    private ArrayList<Integer> actors = new ArrayList<>();
    private ArrayList<Integer> directors = new ArrayList<>();


    Movie(
            String movieTitle,
            String moviePlot,
            String movieReleased,
            String movieIMDBVotes,
            Double movieIMDBRating,
            Integer movieID
    ) {
        this.movietitle = movieTitle;
        this.moviePlot = moviePlot;
        this.movieReleased = movieReleased;
        this.movieIMDBVotes = movieIMDBVotes;
        this.movieIMDBRating = movieIMDBRating;
        if (movieIMDBRating == 0) {
            this.movieIMDBRating = 1.0;
        }
        this.movieID = movieID;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "movietitle='" + movietitle + '\'' +
                ", moviePlot='" + moviePlot + '\'' +
                ", movieReleased='" + movieReleased + '\'' +
                ", movieIMDBVotes='" + movieIMDBVotes + '\'' +
                ", movieIMDBRating=" + movieIMDBRating +
                ", movieID=" + movieID +
                ", currentWeight=" + currentWeight +
                ", genreNames=" + genreNames +
                ", ratedBy=" + ratedBy +
                ", actors=" + actors +
                ", directors=" + directors +
                '}';
    }

    /*
    *   compareTo ist überschrieben, um bei den Filmempfehlungen die Filme nur nach dem Gewicht zu sortieren,
    *   da uns für die Empfehlung kein anderer Faktor interessiert und die MovieOBjekte sonst nicht sortiert werden müssen.
     */
    @Override
    public int compareTo(Object o) {
        Movie other = (Movie) o;
        return other.currentWeight.compareTo(this.currentWeight);
    }

    void addGenre(String genre) {
        genreNames.add(genre);
    }

    void addActor(Integer actorId) {
        actors.add(actorId);
    }

    void addDirector(Integer directorId) {
        directors.add(directorId);
    }

    void addRatedBy(String name) {
        ratedBy.add(name);
    }

    public ArrayList<String> getRatedBy() {
        return ratedBy;
    }

    public void setRatedBy(ArrayList<String> ratedBy) {
        this.ratedBy = ratedBy;
    }

    public String getMovietitle() {
        return movietitle;
    }

    public void setMovietitle(String movietitle) {
        this.movietitle = movietitle;
    }

    public String getMoviePlot() {
        return moviePlot;
    }

    public void setMoviePlot(String moviePlot) {
        this.moviePlot = moviePlot;
    }

    public ArrayList<String> getGenreNames() {
        return genreNames;
    }

    public void setGenreNames(ArrayList<String> genreNames) {
        this.genreNames = genreNames;
    }

    public String getMovieReleased() {
        return movieReleased;
    }

    public void setMovieReleased(String movieReleased) {
        this.movieReleased = movieReleased;
    }

    public Integer getMovieID() {
        return movieID;
    }

    public void setMovieID(Integer movieID) {
        this.movieID = movieID;
    }

    public ArrayList<Integer> getActors() {
        return actors;
    }

    public void setActors(ArrayList<Integer> actors) {
        this.actors = actors;
    }

    public ArrayList<Integer> getDirectors() {
        return directors;
    }

    public void setDirectors(ArrayList<Integer> directors) {
        this.directors = directors;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public String getMovieIMDBVotes() {
        return movieIMDBVotes;
    }

    public void setMovieIMDBVotes(String movieIMDBVotes) {
        this.movieIMDBVotes = movieIMDBVotes;
    }

    public Double getMovieIMDBRating() {
        return movieIMDBRating;
    }

    public void setMovieIMDBRating(Double movieIMDBRating) {
        this.movieIMDBRating = movieIMDBRating;
    }

}
