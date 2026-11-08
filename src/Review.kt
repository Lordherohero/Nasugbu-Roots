class Review(
    val reviewId: Int,
    val userId: Int,
    val placeId: Int,
    val guideId: Int?,
    val rating: Double,
    val comment: String,
    val date: String
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