package de.dhbw.recommender

class Vector(val values: Array[Int]) {

  /**
    * Computes the euclidian norm to the current vector.
    *
    * @return the euclidian norm.
    */
  def computeEuclidianNorm(): Double = {
    var result: Double = 0
    values.foreach(v => {
      result += v * v
    })
    math.sqrt(result)
  }

  /**
    * Computes the scalar product to another vector.
    *
    * @param other The other vector to be used.
    * @return The result of the scalar prodcut.
    */
  def scalarProduct(other: Vector): Double = {
    assert(values.length == other.values.length)

    var sum = 0.0
    var i = 0
    while (i < values.length) {
      sum += (values(i) * other.values(i))
      i += 1
    }
    sum
  }

  /**
    * Computes the mean of the vector and returns it.
    *
    * @return The vector mean.
    */
  def computeMean(): Double = {

    var sum = 0.0
    var i = 0
    while (i < values.length) {
      sum += values(i)
      i += 1
    }
    sum / values.length
  }

  /**
    * Computes the mean over a vector while excluding one value.
    * This is important if one wants to compute the mean over a vector which includes values that represent "no rating given".
    *
    * @param value The value to exclude.
    * @return The mean without the value.
    */
  def computeMeanExcludingValue(value: Int): Double = {

    val valuesExcld: Array[Int] = values.filter(v => !v.equals(value))
    var sum = 0.0
    var i = 0
    while (i < valuesExcld.length) {
      sum += valuesExcld(i)
      i += 1
    }
    sum / valuesExcld.length
  }
}
