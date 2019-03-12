package de.dhbw.recommender

import java.util

import de.dhbw.movieDB.{Movie, MovieDB, User}

class MatrixRepresentation(movieDB: MovieDB) {

  val movieMap: util.HashMap[Integer, Movie] = movieDB.getMovie
  val userMap: util.HashMap[String, User] = movieDB.getUser

  val highestID: Int = movieMap.keySet().toArray.map(i => i.asInstanceOf[Int]).max

  userMap.keySet.toArray.sorted
}
