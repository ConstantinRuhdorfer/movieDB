package de.dhbw.recommender

import java.util

import de.dhbw.movieDB.{Movie, MovieDB, User}

/**
  * A class for doing in memory collaborative filtering by hand.
  * I suspect that this dataset is way too small for quality work in recommender systems and i miht migrate to movielens in the future.
  */
class InMemoryCollaborativeFiltering(movieDB: MovieDB, datasetSize: Int) {

  private val userMap: util.HashMap[String, User] = movieDB.getUser
  private val movieMap: util.HashMap[Integer, Movie] = movieDB.getMovie
  private var userToUserVector: Map[User, UserVector] = _

  userMap.values.stream.forEach(user => userToUserVector += (user -> UserVector(user, datasetSize)))


  /**
    * Computes all predicted ratings for the given user.
    *
    * @param currentUser The user.
    * @return All predicted ratings.
    */
  def aggregationFunction(currentUser: User): Unit = {

    val ratedIDs = currentUser.getRatedMovieIDs
    val notYetRated = movieMap.keySet.toArray.filter(key => !(ratedIDs contains key))

    notYetRated.foreach(movieId => {
      val movie = movieMap.get(movieId)
      val sum: Double = movie.getRatedBy.toArray.map(userId => {
        userToUserVector.get(currentUser) match {
          case Some(uv: UserVector) => userToUserVector.get(userMap.get(userId)) match {
            case Some(otherUv: UserVector) => aggregationSumPerUser(uv, otherUv, userMap.get(userId).getRatedMovie.get(movieId), movie)
          }
        }
      }).sum

      // TODO: Still need to compute the actual predicted rating.
      val phi: Double = normalizationWeight(movie, currentUser)
    })
  }

  /**
    * Computes the inner sum as \sum_{u' \in  I_{u,u'}}{sim(u,u')(v_{u,j} - \bar{v}}
    *
    * @param user        the user.
    * @param otherUser   The user too compare too.
    * @param otherRating The other users rating.
    * @param movie       The movie in question.
    * @return The result.
    */
  private def aggregationSumPerUser(user: UserVector, otherUser: UserVector, otherRating: Double, movie: Movie): Double = {
    val sim: Double = cosineSimilarity(user.userVector, otherUser.userVector)
    sim * (otherRating - otherUser.userVector.computeMeanExcludingValue(0.0))
  }

  /**
    * Phi is calculated by phi = 1 / \sum_{u' \in I_{u,u'}}{|sim(u,u')|}
    *
    * @param movie       The current movie.
    * @param currentUser The current user.
    * @return phi
    */
  private def normalizationWeight(movie: Movie, currentUser: User): Double = {
    movie.getRatedBy.toArray().map(userId => {
      userToUserVector.get(currentUser) match {
        case Some(uv: UserVector) => userToUserVector.get(userMap.get(userId)) match {
          case Some(otherUv: UserVector) => doubleToPosDouble(cosineSimilarity(uv.userVector, otherUv.userVector))
        }
      }
    }).sum
  }

  /**
    * Makes a double positive.
    *
    * @param d Value.
    * @return The positive double.
    */
  private def doubleToPosDouble(d: Double): Double = if (d < 0) d * (-1) else d

  /**
    * Computes the cosine similarity between two vectors in a euclidean vector space.
    * Notice: This implementation considers all Elements in the vector space.
    *
    * @param vectorA First vector for comparison.
    * @param vectorB Second vector for comparison.
    * @return The cosine similarity.
    */
  def cosineSimilarity(vectorA: Vector, vectorB: Vector): Double = {
    //TODO: At the moment this implementation considers all Elements in the vector space. We need to compute I_{u,u'} first.
    vectorA.scalarProduct(vectorB) / (vectorA.computeEuclidianNorm() * vectorB.computeEuclidianNorm())
  }

  /**
    * Computes an Array of Movies which where rated by both users.
    *
    * @param userA The first user.
    * @param userB The second user.
    * @return Returns an Array of Movies rated by both users.
    */
  def getRatedByBoth(userA: User, userB: User): Array[Movie] = userA.getRatedMovieIDs.toArray
    .filter(id => userB.getRatedMovieIDs.toArray contains id)
    .map(id => movieDB.getMovie.get(id))
}
