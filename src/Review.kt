class Review(
    val reviewId: String = "",
    val userId: Int = 0,
    val spotId: Int = 0,
    val guideId: Int = 0,
    val rating: Double = 0.0,
    val comment: String = "",
    val date: String = ""
) {
    fun isValidRating(): Boolean {
        return rating in 1..5
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