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
        println("8. View reviews of a guide")
        println("9. View spot details")
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
            "8" -> viewGuideReviews()
            "9" -> viewSpotDetails()
            "0" -> {
                loggedIn = false
                println("Logged out")
            }
            else -> println("Invalid option")
        }
        saveAll()
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
        println("3. View my reviews")
        println("4. Update my rate")
        println("5. Update my bio")
        println("6. View my profile")
        println("0. Log out")
        val choice = readText("Choose an option: ")
        println()

        when (choice) {
            "1" -> showGuideBookings()
            "2" -> guideUpdateBooking()
            "3" -> showReviewList(guideReviews(currentUserId))
            "4" -> guideUpdateRate(guide)
            "5" -> guideUpdateBio(guide)
            "6" -> guide.displayUser()
            "0" -> {
                loggedIn = false
                println("Logged out")
            }
            else -> println("Invalid option")
        }
        saveAll()
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
        saveAll()
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
        saveAll()
        println()
    }
}