
package com.mygroup.nasugburoots.data

import com.google.firebase.firestore.FirebaseFirestore
import com.mygroup.nasugburoots.model.Review

object ReviewRepository {

    private val db: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    // Save a new destination review
    fun submitReview(
        review: Review,
        onComplete: (Result<Review>) -> Unit
    ) {
        if (review.userId.isBlank()) {
            onComplete(Result.failure(
                IllegalArgumentException("User ID is required.")
            ))
            return
        }

        if (review.placeId <= 0) {
            onComplete(Result.failure(
                IllegalArgumentException("Invalid destination.")
            ))
            return
        }

        if (!review.isValidRating()) {
            onComplete(Result.failure(
                IllegalArgumentException("Rating must be between 1 and 5.")
            ))
            return
        }

        if (!review.hasComment()) {
            onComplete(Result.failure(
                IllegalArgumentException("Please write a comment.")
            ))
            return
        }

        val document = db.collection("reviews").document()
        val reviewToSave = review.copy(
            reviewId = document.id,
            comment = review.comment.trim(),
            reviewerName = review.reviewerName.trim(),
            createdAt = null
        )

        document.set(reviewToSave)
            .addOnSuccessListener {
                onComplete(Result.success(reviewToSave))
            }
            .addOnFailureListener { exception ->
                onComplete(Result.failure(exception))
            }
    }

    // Retrieve all reviews for a destination
    fun getReviewsForPlace(
        placeId: Int,
        onComplete: (Result<List<Review>>) -> Unit
    ) {
        db.collection("reviews")
            .whereEqualTo("placeId", placeId)
            .get()
            .addOnSuccessListener { snapshot ->
                val reviews = snapshot.documents.mapNotNull { document ->
                    document.toObject(Review::class.java)
                }.sortedByDescending {
                    it.createdAt?.toDate()?.time ?: 0L
                }

                onComplete(Result.success(reviews))
            }
            .addOnFailureListener { exception ->
                onComplete(Result.failure(exception))
            }
    }

    // Calculate the average rating
    fun calculateAverageRating(reviews: List<Review>): Double {
        if (reviews.isEmpty()) return 0.0

        return reviews.map { it.rating }.average()
    }
}
