fun main() {
    val tourist = Tourist(1, "Juan", "Onetot@gmail.com")
    val guide = Guide(2, "Janzent", "JanzenKumakaldag@gmail.com", "Tagalog, English", 150.0, "Local guide", true)

    val spots = listOf(
        TouristSpot(1, "Sample Heritage Site", "A sample description", "Historical", true, true, "sample1.jpg", 14.07, 120.63, 3, true, 50.0),
        TouristSpot(2, "Sample Museum", "Another description", "Museum", false, true, "sample2.jpg", 14.08, 120.64, 3, false, 0.0)
    )

    tourist.setUserBio("Loves history")
    tourist.displayUser()
    println()
    guide.displayUser()
    println()

    for (spot in spots) {
        if (spot.searchByName("heritage")) {
            println("Found by name: ${spot.getPlaceName()}")
        }
        if (spot.searchByCategory("museum")) {
            println("Found by category: ${spot.getPlaceName()}")
        }
    }
    println()

    val booking = Booking(1, 1, tourist.getUserId(), guide.getUserId(), "2026-12-05", 3, guide.getRatePerHour(), spots[0].getEntryFee())
    booking.displayBooking()
    booking.confirmBooking()
    booking.cancelBooking()
    booking.confirmBooking()
    println()

    val review = Review(1, tourist.getUserId(), 1, guide.getUserId(), 4.5, "Great tour!", "2026-12-06")
    review.displayReview()
    println("Valid rating: ${review.isValidRating()}")
    println("Has comment: ${review.hasComment()}")
}