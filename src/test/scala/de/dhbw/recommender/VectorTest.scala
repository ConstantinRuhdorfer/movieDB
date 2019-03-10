package de.dhbw.recommender

import org.scalatest.FunSuite

class VectorTest extends FunSuite {

  test("Vector.computeEuclidianNorm") {
    val vectorA = new Vector(Array(1,2,3))
    assert(vectorA.computeEuclidianNorm() == math.sqrt(14))

  }

  test("Vector.scalarProduct") {
    val vectorA = new Vector(Array(1,2,3))
    val vectorB = new Vector(Array(4,5,6))
    assert(vectorA.scalarProduct(vectorB) == 32)
  }
}

