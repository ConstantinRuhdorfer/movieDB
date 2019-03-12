package de.dhbw.recommender

import de.dhbw.movieDB.Main.movieDB
import de.dhbw.movieDB.User
import org.scalatest.FunSuite

class paradigmTest extends FunSuite {

  test("UserVector") {
    val omar = new User("TestUser", 1.0, 1)
    val uv = UserVector(omar, 3)
    assert(uv.userVector.values sameElements Array(0.0,1.0,0.0,0.0))
  }
}
