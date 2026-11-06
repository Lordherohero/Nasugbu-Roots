import java.time.LocalDate
import java.time.YearMonth

val tourist = Tourist(1, "Maria", "maria@example.com")
val guides = mutableListOf<Guide>()
val spots = mutableListOf<TouristSpot>()
val bookings = mutableListOf<Booking>()
val reviews = mutableListOf<Review>()

fun readText(prompt: String): String {
    print(prompt)
    return readln().trim()
}

fun readInt(prompt: String): Int {
    while (true) {
        val input = readText(prompt).toIntOrNull()
        if (input != null) {
            return input
        }
        println("Please enter a whole number")
    }
}

fun readDouble(prompt: String): Double {
    while (true) {
        val input = readText(prompt).toDoubleOrNull()
        if (input != null) {
            return input
        }
        println("Please enter a number")
    }
}

fun readFutureDate(prompt: String): String {
    while (true) {
        val input = readText(prompt)
        val parts = input.split("-")
        if (parts.size != 3) {
            println("Use the format YYYY-MM-DD, for example 2026-12-05")
            continue
        }
        val year = parts[0].toIntOrNull()
        val month = parts[1].toIntOrNull()
        val day = parts[2].toIntOrNull()
        if (year == null || month == null || day == null) {
            println("Year, month, and day must be numbers")
            continue
        }
        if (month < 1 || month > 12) {
            println("Month must be between 1 and 12")
            continue
        }
        if (day < 1 || day > YearMonth.of(year, month).lengthOfMonth()) {
            println("That day does not exist in that month")
            continue
        }
        val date = LocalDate.of(year, month, day)
        if (date.isBefore(LocalDate.now())) {
            println("The date cannot be in the past")
            continue
        }
        return date.toString()
    }
}

fun chooseGuide(): Guide? {
    showGuides()
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

fun loadSampleData() {
    guides.add(Guide(2, "Jun", "jun@example.com", "Tagalog, English", 150.0, "Local guide", true))
    guides.add(Guide(3, "Ana", "ana@example.com", "English, Filipino", 200.0, "Heritage tour guide", true))

    spots.add(TouristSpot(1, "Sample Heritage Site", "A sample description", "Historical", true, true, "sample1.jpg", 14.07, 120.63, 4, true, 50.0))
    spots.add(TouristSpot(2, "Sample Museum", "Another description", "Museum", false, true, "sample2.jpg", 14.08, 120.64, 4, false, 0.0))
    spots.add(TouristSpot(3, "Sample Old Church", "A third description", "Historical", true, false, "sample3.jpg", 14.09, 120.65, 4, true, 20.0))
}

fun findSpot(id: Int): TouristSpot? {
    for (spot in spots) {
        if (spot.getPlaceId() == id) {
            return spot
        }
    }
    return null
}

fun findGuide(id: Int): Guide? {
    for (guide in guides) {
        if (guide.getUserId() == id) {
            return guide
        }
    }
    return null
}

fun findBooking(id: Int): Booking? {
    for (booking in bookings) {
        if (booking.getBookingId() == id) {
            return booking
        }
    }
    return null
}

fun showSpots(list: List<TouristSpot>) {
    if (list.isEmpty()) {
        println("No spots found")
        return
    }
    for (spot in list) {
        println("ID: ${spot.getPlaceId()} | ${spot.getPlaceName()} (${spot.getCategory()}) | Fee: ${spot.getEntryFee()} | Available: ${spot.getAvailability()} | Verified: ${spot.getVerified()}")
    }
}

fun showGuides() {
    for (guide in guides) {
        println("ID: ${guide.getUserId()} | ${guide.getUserName()} | Languages: ${guide.getUserLanguages()} | Rate: ${guide.getRatePerHour()} per hour")
    }
}

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

    val date = readFutureDate("Date (YYYY-MM-DD): ")
    val hours = readInt("Hours: ")
    if (hours <= 0) {
        println("Hours must be positive")
        return
    }

    val booking = Booking(bookings.size + 1, spot.getPlaceId(), tourist.getUserId(), guideId, date, hours, guideRate, spot.getEntryFee())
    bookings.add(booking)
    println("Booking created")
    booking.displayBooking()
}

fun viewBookings() {
    if (bookings.isEmpty()) {
        println("No bookings yet")
        return
    }
    for (booking in bookings) {
        booking.displayBooking()
        println()
    }
}

fun updateBooking() {
    if (bookings.isEmpty()) {
        println("No bookings yet")
        return
    }
    viewBookings()
    val booking = findBooking(readInt("Enter booking ID: "))
    if (booking == null) {
        println("Booking not found")
        return
    }
    val choice = readText("1 to confirm, 2 to cancel: ")
    if (choice == "1") {
        booking.confirmBooking()
    } else if (choice == "2") {
        booking.cancelBooking()
    } else {
        println("Invalid choice")
    }
}

fun leaveReview() {
    showSpots(spots)
    val spot = findSpot(readInt("Enter spot ID: "))
    if (spot == null) {
        println("Spot not found")
        return
    }
    val rating = readDouble("Rating (1 to 5): ")
    val comment = readText("Comment: ")
    val date = LocalDate.now().toString()

    val review = Review(reviews.size + 1, tourist.getUserId(), spot.getPlaceId(), null, rating, comment, date)
    if (!review.isValidRating()) {
        println("Rating must be between 1 and 5")
        return
    }
    reviews.add(review)
    println("Review added")
}

fun viewReviews() {
    showSpots(spots)
    val spot = findSpot(readInt("Enter spot ID: "))
    if (spot == null) {
        println("Spot not found")
        return
    }
    var found = 0
    for (review in reviews) {
        if (review.placeId == spot.getPlaceId()) {
            review.displayReview()
            println()
            found++
        }
    }
    if (found == 0) {
        println("No reviews for this spot yet")
    }
}

fun verifySpot() {
    val unverified = mutableListOf<TouristSpot>()
    for (spot in spots) {
        if (!spot.getVerified()) {
            unverified.add(spot)
        }
    }
    if (unverified.isEmpty()) {
        println("All spots are already verified")
        return
    }
    showSpots(unverified)
    val spot = findSpot(readInt("Enter spot ID to verify: "))
    if (spot == null) {
        println("Spot not found")
        return
    }
    if (spot.getVerified()) {
        println("That spot is already verified")
        return
    }
    spot.setVerified(true)
}

fun printMenu() {
    println("=== Nasugbu Roots ===")
    println("1. View all spots")
    println("2. Search spots")
    println("3. Book a visit")
    println("4. View my bookings")
    println("5. Confirm or cancel a booking")
    println("6. Leave a review")
    println("7. View reviews of a spot")
    println("8. Verify a spot (manager)")
    println("0. Exit")
}

fun main() {
    loadSampleData()
    var running = true

    while (running) {
        printMenu()
        val choice = readText("Choose an option: ")
        println()

        when (choice) {
            "1" -> showSpots(spots)
            "2" -> searchSpots()
            "3" -> bookVisit()
            "4" -> viewBookings()
            "5" -> updateBooking()
            "6" -> leaveReview()
            "7" -> viewReviews()
            "8" -> verifySpot()
            "0" -> {
                running = false
                println("Goodbye!")
            }
            else -> println("Invalid option")
        }
        println()
    }
}