abstract class Booking(
    private val placeId: Int = 0,
    private val travelerId: Int = 0,
    private val guideId: Int = 0,
    protected var status: String = "",
    protected var placeRate: Double = 0.0
){
    abstract fun setPlaceId(): Int
    abstract fun setTravelerId(): Int
    abstract fun setGuideId(): Int
    abstract fun setStatus(): String
    abstract fun setPlaceRate(): Double

    // fun confirmBooking()
    // fun cancelBooking()
    // fun updateStatus(newStatus: String)
    // fun displayBooking()

    fun getPlaceId() {
        println("$placeId -> $placeRate")
    }
    fun getTravelerId() {
        println("$travelerId")
    }
    fun getGuideId() {
        println("$guideId")
    }
    fun getStatus() {
        println("Booking $status")
    }
    fun getPlaceRate(){
        println("Place $placeRate")
    }
}