class Booking(
    val bookingId: Int,
    val placeId: Int,
    val touristId: Int,
    val guideId: Int?,
    val date: String,
    val hours: Int,
    val guideRate: Double,
    val placeFee: Double
) {
    var status: String = "Pending"
        private set

    init {
        require(bookingId > 0) { "Booking ID must be positive" }
        require(placeId > 0) { "Place ID must be positive" }
        require(touristId > 0) { "Tourist ID must be positive" }
        require(hours > 0) { "Hours must be positive" }
        require(guideRate >= 0.0) { "Guide rate cannot be negative" }
        require(placeFee >= 0.0) { "Place fee cannot be negative" }
    }

    fun totalCost(): Double {
        if (guideId != null) {
            return placeFee + guideRate * hours
        }
        return placeFee
    }

    fun confirmBooking() {
        if (status == "Cancelled") {
            println("Cannot confirm a cancelled booking")
            return
        }
        status = "Confirmed"
        println("Booking status updated to: $status")
    }

    fun cancelBooking() {
        status = "Cancelled"
        println("Booking status updated to: $status")
    }

    fun displayBooking() {
        println("Booking ID: $bookingId")
        println("Place ID: $placeId")
        println("Tourist ID: $touristId")
        println("Guide ID: ${guideId ?: "No guide"}")
        println("Date: $date")
        println("Hours: $hours")
        println("Status: $status")
        println("Total Cost: ${totalCost()}")
    }
}