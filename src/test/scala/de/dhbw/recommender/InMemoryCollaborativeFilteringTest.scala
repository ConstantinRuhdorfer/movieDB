package de.dhbw.recommender

import de.dhbw.movieDB.Main.movieDB
import org.scalatest.FunSuite

class InMemoryCollaborativeFilteringTest extends FunSuite {

  test("InMemoryCollaborativeFiltering.getRatedByBoth") {
    val userMap = movieDB.getUser
    val omar = userMap.get("Omar Huffman")
    val jessica = userMap.get("Jessica Sherman")
    val iMCollabFiltering = new InMemoryCollaborativeFiltering(movieDB)

    assert(iMCollabFiltering.getRatedByBoth(omar, jessica)
      .map(movie => movie.getMovieID)
      .map{_.toInt} sameElements Array(2399,1534,1684,1762,1130))
  }
}
