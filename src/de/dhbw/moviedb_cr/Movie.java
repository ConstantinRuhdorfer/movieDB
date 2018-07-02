package de.dhbw.moviedb_cr;

import java.util.ArrayList;

public class Movie {

    private String movietitle;
    private String moviePlot;
    private String genreName;
    private String movieReleased;
    private String movieIMDVotes;
    private String movieIMDRating;
    private Integer movieID;

    ArrayList<Integer> actors = new ArrayList<>();
    ArrayList<Integer> directors = new ArrayList<>();


    Movie(
            String movieTitle,
            String moviePlot,
            String genreName,
            String movieReleased,
            String movieIMDVotes,
            String movieIMDRating,
            Integer movieID
    ) {
        this.movietitle = movieTitle;
        this.moviePlot = moviePlot;
        this.genreName = genreName;
        this.movieReleased = movieReleased;
        this.movieIMDVotes = movieIMDVotes;
        this.movieIMDRating = movieIMDRating;
        this.movieID = movieID;
    }

    void addActor(Integer actorId) {
        actors.add(actorId);
    }

    void addDirector(Integer directorId) {
        directors.add(directorId);
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

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getMovieReleased() {
        return movieReleased;
    }

    public void setMovieReleased(String movieReleased) {
        this.movieReleased = movieReleased;
    }

    public String getMovieIMDVotes() {
        return movieIMDVotes;
    }

    public void setMovieIMDVotes(String movieIMDVotes) {
        this.movieIMDVotes = movieIMDVotes;
    }

    public String getMovieIMDRating() {
        return movieIMDRating;
    }

    public void setMovieIMDRating(String movieIMDRating) {
        this.movieIMDRating = movieIMDRating;
    }

    public Integer getMovieID() {
        return movieID;
    }

    public void setMovieID(Integer movieID) {
        this.movieID = movieID;
    }

    @Override
    public String toString() {
        return "Movie{" +

                ", movietitle='" + movietitle + '\'' +
                ", moviePlot='" + moviePlot + '\'' +
                ", genreName='" + genreName + '\'' +
                ", movieReleased='" + movieReleased + '\'' +
                ", movieIMDVotes='" + movieIMDVotes + '\'' +
                ", movieIMDRating='" + movieIMDRating + '\'' +
                ", movieID=" + movieID +
                '}';
    }
}
