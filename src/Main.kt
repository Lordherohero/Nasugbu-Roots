import java.time.LocalDate
import java.time.YearMonth

val users = mutableListOf<AppUser>()
val guides = mutableListOf<Guide>()
val spots = mutableListOf<TouristSpot>()
val bookings = mutableListOf<Booking>()
val reviews = mutableListOf<Review>()
var currentUserId = 0

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

fun showGuides(list: List<Guide>) {
    if (list.isEmpty()) {
        println("No guides found")
        return
    }
    for (guide in list) {
        println("ID: ${guide.getUserId()} | ${guide.getUserName()} | Languages: ${guide.getUserLanguages()} | Rate: ${guide.getRatePerHour()} per hour | Verified: ${guide.getUserVerified()}")
    }
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

    val review = Review(reviews.size + 1, currentUserId, spot.getPlaceId(), null, rating, comment, date)
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

fun showGuideBookings(): Int {
    var found = 0
    for (booking in bookings) {
        if (booking.getGuideId() == currentUserId) {
            booking.displayBooking()
            println()
            found++
        }
    }
    if (found == 0) {
        println("No bookings are assigned to you yet")
    }
    return found
}

fun guideUpdateBooking() {
    if (showGuideBookings() == 0) {
        return
    }
    val booking = findBooking(readInt("Enter booking ID: "))
    if (booking == null) {
        println("Booking not found")
        return
    }
    if (booking.getGuideId() != currentUserId) {
        println("That booking is not assigned to you")
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

fun guideUpdateRate(guide: Guide) {
    val rate = readDouble("New rate per hour: ")
    if (rate < 0.0) {
        println("Rate cannot be negative")
        return
    }
    guide.setRatePerHour(rate)
}

fun guideUpdateBio(guide: Guide) {
    val bio = readText("New bio: ")
    if (bio.isBlank()) {
        println("Bio cannot be blank")
        return
    }
    guide.setUserBio(bio)
}

fun chooseOwnSpot(): TouristSpot? {
    val mine = mutableListOf<TouristSpot>()
    for (spot in spots) {
        if (spot.getOwnerId() == currentUserId) {
            mine.add(spot)
        }
    }
    if (mine.isEmpty()) {
        println("You have no spots yet")
        return null
    }
    showSpots(mine)
    val spot = findSpot(readInt("Enter spot ID: "))
    if (spot == null) {
        println("Spot not found")
        return null
    }
    if (spot.getOwnerId() != currentUserId) {
        println("That spot is not yours")
        return null
    }
    return spot
}

fun showMySpots() {
    val mine = mutableListOf<TouristSpot>()
    for (spot in spots) {
        if (spot.getOwnerId() == currentUserId) {
            mine.add(spot)
        }
    }
    if (mine.isEmpty()) {
        println("You have no spots yet")
        return
    }
    showSpots(mine)
}

fun addSpot() {
    val name = readText("Place name: ")
    if (name.isBlank()) {
        println("Place name cannot be blank")
        return
    }
    val description = readText("Description: ")
    val category = readText("Category: ")
    val allowsGuides = readText("Allow guides? (y/n): ").equals("y", ignoreCase = true)
    val fee = readDouble("Entry fee: ")
    if (fee < 0.0) {
        println("Entry fee cannot be negative")
        return
    }
    val imageUrl = readText("Image file name or URL: ")
    val latitude = readDouble("Latitude: ")
    if (latitude < -90.0 || latitude > 90.0) {
        println("Latitude must be between -90 and 90")
        return
    }
    val longitude = readDouble("Longitude: ")
    if (longitude < -180.0 || longitude > 180.0) {
        println("Longitude must be between -180 and 180")
        return
    }

    val spot = TouristSpot(spots.size + 1, name, description, category, allowsGuides, true, imageUrl, latitude, longitude, currentUserId, false, fee)
    spots.add(spot)
    println("Spot added. A manager needs to verify it")
}

fun editSpotDetails() {
    val spot = chooseOwnSpot()
    if (spot == null) {
        return
    }
    println("1. Name")
    println("2. Category")
    println("3. Description")
    println("4. Image")
    val choice = readText("What do you want to change? ")
    if (choice != "1" && choice != "2" && choice != "3" && choice != "4") {
        println("Invalid choice")
        return
    }
    val value = readText("New value: ")
    if (value.isBlank()) {
        println("Value cannot be blank")
        return
    }
    when (choice) {
        "1" -> spot.setPlaceName(value)
        "2" -> spot.setCategory(value)
        "3" -> spot.setDescription(value)
        "4" -> spot.setImageUrl(value)
    }
}

fun changeEntryFee() {
    val spot = chooseOwnSpot()
    if (spot == null) {
        return
    }
    val fee = readDouble("New entry fee: ")
    if (fee < 0.0) {
        println("Entry fee cannot be negative")
        return
    }
    spot.setEntryFee(fee)
}

fun changeAvailability() {
    val spot = chooseOwnSpot()
    if (spot == null) {
        return
    }
    spot.setAvailability(!spot.getAvailability())
}

fun changeAllowsGuides() {
    val spot = chooseOwnSpot()
    if (spot == null) {
        return
    }
    spot.setAllowsGuides(!spot.getAllowsGuides())
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

fun verifyGuide() {
    val unverified = mutableListOf<Guide>()
    for (guide in guides) {
        if (!guide.getUserVerified()) {
            unverified.add(guide)
        }
    }
    if (unverified.isEmpty()) {
        println("All guides are already verified")
        return
    }
    showGuides(unverified)
    val guide = findGuide(readInt("Enter guide ID to verify: "))
    if (guide == null) {
        println("Guide not found")
        return
    }
    if (guide.getUserVerified()) {
        println("That guide is already verified")
        return
    }
    guide.setUserVerified(true)
}

fun touristMenu() {
    var loggedIn = true
    while (loggedIn) {
        println("=== Tourist Menu ===")
        println("1. View all spots")
        println("2. Search spots")
        println("3. Book a visit")
        println("4. View my bookings")
        println("5. Cancel a booking")
        println("6. Leave a review")
        println("7. View reviews of a spot")
        println("8. View spot details")
        println("0. Log out")
        val choice = readText("Choose an option: ")
        println()

        when (choice) {
            "1" -> showSpots(spots)
            "2" -> searchSpots()
            "3" -> bookVisit()
            "4" -> viewMyBookings()
            "5" -> cancelMyBooking()
            "6" -> leaveReview()
            "7" -> viewReviews()
            "8" -> viewSpotDetails()
            "0" -> {
                loggedIn = false
                println("Logged out")
            }
            else -> println("Invalid option")
        }
        println()
    }
}

fun guideMenu() {
    val guide = findGuide(currentUserId)
    if (guide == null) {
        println("Guide profile not found")
        return
    }
    var loggedIn = true
    while (loggedIn) {
        println("=== Guide Menu ===")
        println("1. View my bookings")
        println("2. Confirm or cancel a booking")
        println("3. Update my rate")
        println("4. Update my bio")
        println("5. View my profile")
        println("0. Log out")
        val choice = readText("Choose an option: ")
        println()

        when (choice) {
            "1" -> showGuideBookings()
            "2" -> guideUpdateBooking()
            "3" -> guideUpdateRate(guide)
            "4" -> guideUpdateBio(guide)
            "5" -> guide.displayUser()
            "0" -> {
                loggedIn = false
                println("Logged out")
            }
            else -> println("Invalid option")
        }
        println()
    }
}

fun ownerMenu() {
    var loggedIn = true
    while (loggedIn) {
        println("=== Spot Owner Menu ===")
        println("1. View my spots")
        println("2. Add a spot")
        println("3. Edit spot details")
        println("4. Change entry fee")
        println("5. Change availability")
        println("6. Change whether guides are allowed")
        println("0. Log out")
        val choice = readText("Choose an option: ")
        println()

        when (choice) {
            "1" -> showMySpots()
            "2" -> addSpot()
            "3" -> editSpotDetails()
            "4" -> changeEntryFee()
            "5" -> changeAvailability()
            "6" -> changeAllowsGuides()
            "0" -> {
                loggedIn = false
                println("Logged out")
            }
            else -> println("Invalid option")
        }
        println()
    }
}

fun managerMenu() {
    var loggedIn = true
    while (loggedIn) {
        println("=== Manager Menu ===")
        println("1. View all spots")
        println("2. Verify a spot")
        println("3. Verify a guide")
        println("0. Log out")
        val choice = readText("Choose an option: ")
        println()

        when (choice) {
            "1" -> showSpots(spots)
            "2" -> verifySpot()
            "3" -> verifyGuide()
            "0" -> {
                loggedIn = false
                println("Logged out")
            }
            else -> println("Invalid option")
        }
        println()
    }
}

fun chooseUser(): AppUser? {
    println("=== Nasugbu Roots ===")
    println("Who is using the app?")
    for (user in users) {
        println("ID: ${user.getUserId()} | ${user.getUserName()} | ${user.getUserRole()}")
    }
    while (true) {
        val id = readInt("Enter user ID (0 to exit): ")
        if (id == 0) {
            return null
        }
        for (user in users) {
            if (user.getUserId() == id) {
                return user
            }
        }
        println("No user with that ID. Try again")
    }
}

fun main() {
    loadSampleData()
    var running = true

    while (running) {
        val user = chooseUser()
        if (user == null) {
            running = false
            println("Goodbye!")
        } else {
            currentUserId = user.getUserId()
            println()
            println("Welcome, ${user.getUserName()}")
            println()

            val role = user.getUserRole()
            if (role == "Tourist") {
                touristMenu()
            } else if (role == "Guide") {
                guideMenu()
            } else if (role == "Owner") {
                ownerMenu()
            } else if (role == "Manager") {
                managerMenu()
            }
        }
    }
}