package de.dhbw.recommender

class Vector(var values: Iterable[Int]) {
  values = values.toArray

  def computeEuclidianNorm(): Double = {
    var result: Double = 0
    values.foreach(v => {
      result += v * v
    })
    math.sqrt(result)
  }
}
