fun showGuideBookings(): Int {
    val mine = bookings.filter { booking -> booking.guideId == currentUserId }
    if (mine.isEmpty()) {
        println("No bookings are assigned to you yet")
        return 0
    }
    for (booking in mine) {
        booking.displayBooking()
        println()
    }
    return mine.size
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
    if (booking.guideId != currentUserId) {
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