open class Booking(
    private val placeId: Int,
    private val travelerId: Int,
    private val guideId: Int,
    private var status: String,
    private var placeRate: Double
) {
    init {
        require(placeId > 0) { "Place ID must be positive" }
        require(travelerId > 0) { "Traveler ID must be positive" }
        require(guideId > 0) { "Guide ID must be positive" }
        require(isValidStatus(status)) { "Status must be Pending, Confirmed, or Cancelled" }
        require(placeRate >= 0.0) { "Place rate cannot be negative" }
    }
    private fun isValidStatus(status: String): Boolean {
        return status == "Pending" || status == "Confirmed" || status == "Cancelled"
    }
    // Setters (only for the properties that stay var)
    fun setStatus(status: String) {
        require(isValidStatus(status)) { "Status must be Pending, Confirmed, or Cancelled" }
        this.status = status
        println("Booking status updated to: $status")
    }
    fun setPlaceRate(placeRate: Double) {
        require(placeRate >= 0.0) { "Place rate cannot be negative" }
        this.placeRate = placeRate
        println("Place rate updated to: $placeRate")
    }
    // Booking actions
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
        println("Place ID: $placeId")
        println("Traveler ID: $travelerId")
        println("Guide ID: $guideId")
        println("Status: $status")
        println("Place Rate: $placeRate")
    }
    fun getPlaceId() {
        println("Place ID: $placeId")
    }
    fun getTravelerId() {
        println("Traveler ID: $travelerId")
    }
    fun getGuideId() {
        println("Guide ID: $guideId")
    }
    fun getStatus() {
        println("Booking Status: $status")
    }
    fun getPlaceRate() {
        println("Place Rate: $placeRate")
    }

    class Review(
        val reviewId: String = "",
        val userId: Int = 0,
        val spotId: Int = 0,
        val guideId: Int = 0,
        val rating: Double = 0.0,
        val comment: String = "",
        val date: String = ""
    ) {
        fun isValidRating(): Boolean {
            return rating in 1.0..5.0
        }

        fun hasComment(): Boolean {
            return comment.isNotBlank()
        }

        fun displayReview() {
            println("Rating: $rating/5")
            println("Comment: $comment")
            println("Date: $date")
        }
    }
}