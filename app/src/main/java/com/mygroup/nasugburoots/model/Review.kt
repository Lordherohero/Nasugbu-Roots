package com.mygroup.nasugburoots.model

data class Review(
    val reviewId: Int = 0,
    val userId: Int = 0,
    val placeId: Int = 0,
    val guideId: Int? = null,
    val rating: Double = 0.0,
    val comment: String = "",
    val date: String = ""
) {
    fun isValidRating(): Boolean {
        return rating in 1.0..5.0
    }

    fun hasComment(): Boolean {
        return comment.isNotBlank()
    }

    fun displayReview() {
        println("Rating: $rating/5")
        println("Comment: $comment")
        println("Date: $date")
    }
}