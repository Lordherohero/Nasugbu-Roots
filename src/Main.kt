fun main() {
    val tourist = Tourist(1, "Juan", "OneTot@gmal.com")
    val guide = Guide(2, "Janzent", "JanzentKumakaldag@gmail.com", "Tagalog, English", 150.0, "Local guide", true)

    val spots = listOf(
        TouristSpot(1, "Sample Heritage Site", "A sample description", "Historical", true, true, "sample1.jpg", 14.07, 120.63, 3, true, 50.0),
        TouristSpot(2, "Sample Museum", "Another description", "Museum", false, true, "sample2.jpg", 14.08, 120.64, 3, false, 0.0)
    )

    tourist.displayUser()
    println()
    guide.displayUser()
    println()

    for (spot in spots) {
        if (spot.searchByName("heritage")) {
            println("Found by name: ${spot.placeName}")
        }
        if (spot.searchByCategory("museum")) {
            println("Found by category: ${spot.placeName}")
        }
    }
    println()

    val booking = Booking(1, 1, tourist.userId, guide.userId, "2026-12-05", 3, guide.ratePerHour, spots[0].entryFee)
    booking.displayBooking()
    booking.confirmBooking()
    booking.cancelBooking()
    booking.confirmBooking()
    println()

    val review = Review(1, tourist.userId, 1, guide.userId, 4.5, "Great tour!", "2026-12-06")
    review.displayReview()
    println("Valid rating: ${review.isValidRating()}")
    println("Has comment: ${review.hasComment()}")
}