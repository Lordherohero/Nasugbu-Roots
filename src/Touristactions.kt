import java.time.LocalDate

fun searchSpots() {
    val name = readText("Search by name (leave blank to skip): ")
    val category = readText("Search by category (leave blank to skip): ")
    val results = mutableListOf<TouristSpot>()

    for (spot in spots) {
        val nameMatches = name.isBlank() || spot.searchByName(name)
        val categoryMatches = category.isBlank() || spot.searchByCategory(category)
        if (nameMatches && categoryMatches) {
            results.add(spot)
        }
    }
    showSpots(results)
}

fun viewSpotDetails() {
    showSpots(spots)
    val spot = findSpot(readInt("Enter spot ID: "))
    if (spot == null) {
        println("Spot not found")
        return
    }
    println()
    spot.displaySpot()
}

fun chooseGuide(): Guide? {
    showGuides(guides)
    while (true) {
        val id = readInt("Enter guide ID (0 to book without a guide): ")
        if (id == 0) {
            return null
        }
        val guide = findGuide(id)
        if (guide != null) {
            return guide
        }
        println("No guide with that ID. Try again")
    }
}

fun isSpotBooked(placeId: Int, date: String): Boolean {
    for (booking in bookings) {
        if (booking.getStatus() != "Cancelled" && booking.getPlaceId() == placeId && booking.getDate() == date) {
            return true
        }
    }
    return false
}

fun isGuideBooked(guideId: Int, date: String): Boolean {
    for (booking in bookings) {
        if (booking.getStatus() != "Cancelled" && booking.getGuideId() == guideId && booking.getDate() == date) {
            return true
        }
    }
    return false
}

fun chooseBookingDate(placeId: Int, guideId: Int?): String {
    while (true) {
        val date = readFutureDate("Date (YYYY-MM-DD): ")
        if (isSpotBooked(placeId, date)) {
            println("That spot is already booked on that date. Choose another date")
        } else if (guideId != null && isGuideBooked(guideId, date)) {
            println("That guide is already booked on that date. Choose another date")
        } else {
            return date
        }
    }
}

fun bookVisit() {
    showSpots(spots)
    val spot = findSpot(readInt("Enter spot ID: "))
    if (spot == null) {
        println("Spot not found")
        return
    }
    if (!spot.getAvailability()) {
        println("This spot is not available")
        return
    }

    var guideId: Int? = null
    var guideRate = 0.0

    if (spot.getAllowsGuides()) {
        val answer = readText("Add a guide? (y/n): ")
        if (answer.equals("y", ignoreCase = true)) {
            val guide = chooseGuide()
            if (guide != null) {
                guideId = guide.getUserId()
                guideRate = guide.getRatePerHour()
            }
        }
    }

    val date = chooseBookingDate(spot.getPlaceId(), guideId)
    val hours = readInt("Hours: ")
    if (hours <= 0) {
        println("Hours must be positive")
        return
    }

    val booking = Booking(bookings.size + 1, spot.getPlaceId(), currentUserId, guideId, date, hours, guideRate, spot.getEntryFee())
    bookings.add(booking)
    if (guideId == null) {
        booking.confirmBooking()
    }
    println("Booking created")
    booking.displayBooking()
}

fun countMyBookings(): Int {
    var count = 0
    for (booking in bookings) {
        if (booking.getTouristId() == currentUserId) {
            count++
        }
    }
    return count
}

fun viewMyBookings() {
    if (countMyBookings() == 0) {
        println("You have no bookings yet")
        return
    }
    for (booking in bookings) {
        if (booking.getTouristId() == currentUserId) {
            booking.displayBooking()
            println()
        }
    }
}

fun cancelMyBooking() {
    if (countMyBookings() == 0) {
        println("You have no bookings yet")
        return
    }
    viewMyBookings()
    val booking = findBooking(readInt("Enter booking ID to cancel: "))
    if (booking == null) {
        println("Booking not found")
        return
    }
    if (booking.getTouristId() != currentUserId) {
        println("That is not your booking")
        return
    }
    if (booking.getStatus() == "Cancelled") {
        println("That booking is already cancelled")
        return
    }
    booking.cancelBooking()
}

fun hasReviewedSpot(placeId: Int): Boolean {
    for (review in reviews) {
        if (review.userId == currentUserId && review.placeId == placeId && review.guideId == null) {
            return true
        }
    }
    return false
}

fun visitHasHappened(booking: Booking): Boolean {
    return !LocalDate.parse(booking.getDate()).isAfter(LocalDate.now())
}

fun askReview(placeId: Int, guideId: Int?, label: String): Review {
    while (true) {
        val rating = readDouble("Rating for $label (1 to 5): ")
        val comment = readText("Comment for $label: ")
        val review = Review(reviews.size + 1, currentUserId, placeId, guideId, rating, comment, LocalDate.now().toString())
        if (!review.isValidRating()) {
            println("Rating must be between 1 and 5")
            continue
        }
        if (!review.hasComment()) {
            println("Comment cannot be blank")
            continue
        }
        return review
    }
}

fun leaveReview() {
    val eligible = mutableListOf<Booking>()
    for (booking in bookings) {
        if (booking.getTouristId() == currentUserId && booking.getStatus() == "Confirmed" && visitHasHappened(booking) && !hasReviewedSpot(booking.getPlaceId())) {
            eligible.add(booking)
        }
    }
    if (eligible.isEmpty()) {
        println("You can review a spot once a confirmed booking's date has arrived")
        return
    }

    for (booking in eligible) {
        val spot = findSpot(booking.getPlaceId())
        var spotName = "Unknown spot"
        if (spot != null) {
            spotName = spot.getPlaceName()
        }
        println("Booking ID: ${booking.getBookingId()} | $spotName | ${booking.getDate()}")
    }

    val bookingId = readInt("Enter booking ID to review: ")
    var chosen: Booking? = null
    for (booking in eligible) {
        if (booking.getBookingId() == bookingId) {
            chosen = booking
        }
    }
    if (chosen == null) {
        println("That booking cannot be reviewed")
        return
    }

    val spotReview = askReview(chosen.getPlaceId(), null, "the spot")
    reviews.add(spotReview)
    println("Spot review added")

    val guideId = chosen.getGuideId()
    if (guideId != null) {
        val answer = readText("Also rate your guide? (y/n): ")
        if (answer.equals("y", ignoreCase = true)) {
            val guideReview = askReview(chosen.getPlaceId(), guideId, "your guide")
            reviews.add(guideReview)
            println("Guide review added")
        }
    }
}

fun viewReviews() {
    showSpots(spots)
    val spot = findSpot(readInt("Enter spot ID: "))
    if (spot == null) {
        println("Spot not found")
        return
    }
    showReviewList(spotReviews(spot.getPlaceId()))
}

fun viewGuideReviews() {
    showGuides(guides)
    val guide = findGuide(readInt("Enter guide ID: "))
    if (guide == null) {
        println("Guide not found")
        return
    }
    showReviewList(guideReviews(guide.getUserId()))
}