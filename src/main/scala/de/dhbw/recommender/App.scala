package de.dhbw.recommender

import de.dhbw.movieDB.User

object App {

  def main(): Unit = {
    val inMemCollab = new InMemoryCollaborativeFiltering()

    inMemCollab.averageRatingOfUser(new User("b"))

    val vecA = new Vector(Array(1,2,3,4))
    val vecB = new Vector(Array(1,2,3,4))
    println(vecA.scalarProduct(vecB))
  }
}
