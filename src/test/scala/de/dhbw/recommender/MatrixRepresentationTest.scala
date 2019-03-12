package de.dhbw.recommender

import org.scalatest.FunSuite
import de.dhbw.movieDB.Main.movieDB

class MatrixRepresentationTest extends FunSuite {
  test("MatrixRepresentation.Constructor") {
    val mr = new MatrixRepresentation(movieDB)
  }
}
