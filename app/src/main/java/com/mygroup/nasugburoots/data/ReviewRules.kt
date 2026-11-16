package com.mygroup.nasugburoots.data

import com.mygroup.nasugburoots.model.Review
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun hasReviewedSpot(reviews: List<Review>, userId: String, placeId: Int): Boolean {
    return reviews.any { review ->
        review.userId == userId && review.placeId == placeId && review.guideId == null
    }
}

fun spotReviews(reviews: List<Review>, placeId: Int): List<Review> {
    return reviews.filter { review -> review.placeId == placeId && review.guideId == null }
}

fun averageRating(reviews: List<Review>): Double {
    if (reviews.isEmpty()) {
        return 0.0
    }
    return reviews.sumOf { review -> review.rating } / reviews.size
}

fun todayLocal(): String {
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
}