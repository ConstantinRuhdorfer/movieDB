package de.dhbw.recommender

import java.{lang, util}

import de.dhbw.movieDB.{Movie, User}

/**
  * Stores on predicted Rating for convenience.
  *
  * @param movie  The rated movie.
  * @param rating The rating.
  */
case class PredictedRating(movie: Movie, rating: Double)

/**
  * This case class holds a user Vector.
  * A user vector contains all ratings for all movies where 0 equalus no rating.
  * This is the basis for building the matrix to compute over.
  *
  * @param user      The user.
  * @param highestID The highestID, notice the vector spans from 0 to highestID.
  */
case class UserVector(user: User, highestID: Int) {
  private val ratedMovie: util.HashMap[Integer, lang.Double] = user.getRatedMovie
  val userVector: Vector = new Vector((0 to highestID).map(elem => {
    if (ratedMovie.keySet contains elem) ratedMovie.get(elem).toDouble else 0
  }).toArray)
}
