val users = mutableListOf<AppUser>()
val guides = mutableListOf<Guide>()
val spots = mutableListOf<TouristSpot>()
val bookings = mutableListOf<Booking>()
val reviews = mutableListOf<Review>()
var currentUserId = 0

fun loadSampleData() {
    val jun = Guide(2, "Jun", "jun@example.com", "Tagalog, English", 150.0, "Local guide", true)
    val ana = Guide(3, "Ana", "ana@example.com", "English, Filipino", 200.0, "Heritage tour guide", false)

    users.add(Tourist(1, "Maria", "maria@example.com"))
    users.add(jun)
    users.add(ana)
    users.add(SpotOwner(4, "Rosa", "rosa@example.com", "Owns the sample sites", true))
    users.add(Manager(5, "Leo", "leo@example.com", "App manager", true))
    users.add(Tourist(6, "Carlo", "carlo@example.com"))

    guides.add(jun)
    guides.add(ana)

    spots.add(TouristSpot(1, "Sample Heritage Site", "A sample description", "Historical", true, true, "sample1.jpg", 14.07, 120.63, 4, true, 50.0))
    spots.add(TouristSpot(2, "Sample Museum", "Another description", "Museum", false, true, "sample2.jpg", 14.08, 120.64, 4, false, 0.0))
    spots.add(TouristSpot(3, "Sample Old Church", "A third description", "Historical", true, false, "sample3.jpg", 14.09, 120.65, 4, true, 20.0))
}

fun findSpot(id: Int): TouristSpot? {
    return spots.find { spot -> spot.placeId == id }
}

fun findGuide(id: Int): Guide? {
    return guides.find { guide -> guide.getUserId() == id }
}

fun findBooking(id: Int): Booking? {
    return bookings.find { booking -> booking.bookingId == id }
}

fun spotReviews(placeId: Int): List<Review> {
    return reviews.filter { review -> review.placeId == placeId && review.guideId == null }
}

fun guideReviews(guideId: Int): List<Review> {
    return reviews.filter { review -> review.guideId == guideId }
}