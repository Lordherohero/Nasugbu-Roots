fun showSpots(list: List<TouristSpot>) {
    if (list.isEmpty()) {
        println("No spots found")
        return
    }
    for (spot in list) {
        println("ID: ${spot.placeId} | ${spot.placeName} (${spot.category}) | Fee: ${spot.entryFee} | Available: ${spot.availability} | Verified: ${spot.verified}")
    }
}

fun showGuides(list: List<Guide>) {
    if (list.isEmpty()) {
        println("No guides found")
        return
    }
    for (guide in list) {
        println("ID: ${guide.getUserId()} | ${guide.getUserName()} | Languages: ${guide.getUserLanguages()} | Rate: ${guide.getRatePerHour()} per hour | Verified: ${guide.getUserVerified()}")
    }
}

fun showReviewList(list: List<Review>) {
    if (list.isEmpty()) {
        println("No reviews yet")
        return
    }
    for (review in list) {
        review.displayReview()
        println()
    }
    val total = list.sumOf { review -> review.rating }
    val average = String.format("%.1f", total / list.size)
    println("Average rating: $average (${list.size} reviews)")
}