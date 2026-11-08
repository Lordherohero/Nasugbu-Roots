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