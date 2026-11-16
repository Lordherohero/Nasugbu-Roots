package com.mygroup.nasugburoots.data

import com.google.firebase.firestore.FirebaseFirestore
import com.mygroup.nasugburoots.model.Review

object ReviewRepository {

    private val db: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    fun submitReview(
        review: Review,
        onComplete: (Result<Review>) -> Unit
    ) {
        if (review.userId.isBlank()) {
            onComplete(
                Result.failure(
                    IllegalArgumentException("User ID is required.")
                )
            )
            return
        }

        if (review.placeId <= 0) {
            onComplete(
                Result.failure(
                    IllegalArgumentException("Invalid destination.")
                )
            )
            return
        }

        if (!review.isValidRating()) {
            onComplete(
                Result.failure(
                    IllegalArgumentException("Rating must be between 1 and 5.")
                )
            )
            return
        }

        if (!review.hasComment()) {
            onComplete(
                Result.failure(
                    IllegalArgumentException("Please write a comment.")
                )
            )
            return
        }

        db.collection("reviews")
            .whereEqualTo("userId", review.userId)
            .whereEqualTo("placeId", review.placeId)
            .limit(1)
            .get()
            .addOnSuccessListener { existing ->
                if (!existing.isEmpty) {
                    onComplete(
                        Result.failure(
                            IllegalStateException("You have already reviewed this destination.")
                        )
                    )
                    return@addOnSuccessListener
                }
                saveReview(review, onComplete)
            }
            .addOnFailureListener { exception ->
                onComplete(Result.failure(exception))
            }
    }

    private fun saveReview(
        review: Review,
        onComplete: (Result<Review>) -> Unit
    ) {
        val document = db.collection("reviews")
            .document("${review.userId}_${review.placeId}")

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

    fun hasReviewed(
        userId: String,
        placeId: Int,
        onComplete: (Result<Boolean>) -> Unit
    ) {
        db.collection("reviews")
            .whereEqualTo("userId", userId)
            .whereEqualTo("placeId", placeId)
            .limit(1)
            .get()
            .addOnSuccessListener { existing ->
                onComplete(Result.success(!existing.isEmpty))
            }
            .addOnFailureListener { exception ->
                onComplete(Result.failure(exception))
            }
    }

    fun getReviewsForPlace(
        placeId: Int,
        onComplete: (Result<List<Review>>) -> Unit
    ) {
        db.collection("reviews")
            .whereEqualTo("placeId", placeId)
            .get()
            .addOnSuccessListener { snapshot ->
                val reviews = snapshot.documents
                    .mapNotNull { doc -> doc.toObject(Review::class.java) }
                    .sortedByDescending { review -> review.createdAt?.seconds ?: 0L }
                onComplete(Result.success(reviews))
            }
            .addOnFailureListener { exception ->
                onComplete(Result.failure(exception))
            }
    }
}