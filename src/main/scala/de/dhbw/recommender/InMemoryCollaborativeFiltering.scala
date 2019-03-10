package de.dhbw.recommender


/**
  * A class for doing in memory collaborative filtering by hand.
  * I suspect that this dataset is way too small for quality work in recommender systems and i miht migrate too movielens in the future.
  */
class InMemoryCollaborativeFiltering {

  /**
    * Computes the cosine similarity between two vectors in a euclidian vector space.
    *
    *
    * @param vectorA First vector for comparison.
    * @param vectorB Second vector for comparison.
    * @return The cosine similarity.
    */
  def cosineSimilarity(vectorA: Vector, vectorB: Vector): Double = {
    //TODO: At the moment this implementation considers all Elements in the vector space. We need to compute I_{u,u'} first.
    vectorA.scalarProduct(vectorB) / (vectorA.computeEuclidianNorm() * vectorB.computeEuclidianNorm())
  }
}
