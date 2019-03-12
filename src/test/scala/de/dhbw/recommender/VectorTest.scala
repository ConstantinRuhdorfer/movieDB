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

  test("Vector.computeMean") {
    val vectorA = new Vector(Array(0,3,3))
    assert(vectorA.computeMean() == 2)
  }

  test("Vector.computeMeanExcludingValue") {
    val vectorA = new Vector(Array(0,3,3))
    assert(vectorA.computeMeanExcludingValue(0) == 3)
  }
}

