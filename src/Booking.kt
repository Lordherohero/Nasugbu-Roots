open class Booking(
    private val bookingId: Int,
    private val placeId: Int,
    private val touristId: Int,
    private val guideId: Int?,
    private val date: String,
    private val hours: Int,
    private val guideRate: Double,
    private val placeFee: Double
) {
    private var status: String = "Pending"

    init {
        require(bookingId > 0) { "Booking ID must be positive" }
        require(placeId > 0) { "Place ID must be positive" }
        require(touristId > 0) { "Tourist ID must be positive" }
        require(hours > 0) { "Hours must be positive" }
        require(guideRate >= 0.0) { "Guide rate cannot be negative" }
        require(placeFee >= 0.0) { "Place fee cannot be negative" }
    }

    private fun isValidStatus(status: String): Boolean {
        return status == "Pending" || status == "Confirmed" || status == "Cancelled"
    }

    fun getBookingId(): Int {
        return bookingId
    }

    fun getPlaceId(): Int {
        return placeId
    }

    fun getTouristId(): Int {
        return touristId
    }

    fun getGuideId(): Int? {
        return guideId
    }

    fun getDate(): String {
        return date
    }

    fun getHours(): Int {
        return hours
    }

    fun getStatus(): String {
        return status
    }

    fun setStatus(status: String) {
        require(isValidStatus(status)) { "Status must be Pending, Confirmed, or Cancelled" }
        this.status = status
        println("Booking status updated to: $status")
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
        setStatus("Confirmed")
    }

    fun cancelBooking() {
        setStatus("Cancelled")
    }

    fun displayBooking() {
        println("Booking ID: $bookingId")
        println("Place ID: $placeId")
        println("Tourist ID: $touristId")
        if (guideId == null) {
            println("Guide ID: No guide")
        } else {
            println("Guide ID: $guideId")
        }
        println("Date: $date")
        println("Hours: $hours")
        println("Status: $status")
        println("Total Cost: ${totalCost()}")
    }
}