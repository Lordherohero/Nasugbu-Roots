fun showSpots(list: List<TouristSpot>) {
    if (list.isEmpty()) {
        println("No spots found")
        return
    }
    for (spot in list) {
        println("ID: ${spot.getPlaceId()} | ${spot.getPlaceName()} (${spot.getCategory()}) | Fee: ${spot.getEntryFee()} | Available: ${spot.getAvailability()} | Verified: ${spot.getVerified()}")
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
    var total = 0.0
    for (review in list) {
        review.displayReview()
        println()
        total += review.rating
    }
    val average = String.format("%.1f", total / list.size)
    println("Average rating: $average (${list.size} reviews)")
}