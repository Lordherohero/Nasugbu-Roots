// four kinds of people who use Nasugbu Roots WIP
class AppUser(
    val name: String = "",
    val email: String = "",
    val role: String = "",
    val verified: Boolean = true
)

class TouristSpot(
    val name: String = "",
    val description: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val ownerId: String = "",
    val allowsGuides: Boolean = true,
    val availability: String = "",
    val imageUrl: String = "",
    val approved: Boolean = true
) {
    fun displayInfo()
    fun displayLocation()
    fun checkAvailability()

class GuideProfile(
    val bio: String = "",
    val languages: String = "",
    val ratePerDay: Double = 0.0
) {
    fun displayProfile()
    fun isAvailable(): Boolean
    fun calculateTourCost(days: Int): Double
    fun displayLanguages()

class Booking(
    val bookingId: String = "",
    val spotId: String = "",
    val travelerId: String = "",
    val guideId: String = "",
    val date: String = "",
    val time: String = "",
    val duration: Int = 0,
    val status: String = ""
) {
    fun confirmBooking()
    fun cancelBooking()
    fun updateStatus(newStatus: String)
    fun displayBooking()
// functions planed to be added  (feel free to add any ideas po)
// displayLocation(), showStatus(), verifyUser(), markLoaction(), findLocation()....

class Review(
    val reviewId: String = "",
    val userId: String = "",
    val spotId: String = "",
    val guideId: String = "",
    val rating: Double = 0.0,
    val comment: String = "",
    val date: String = ""
)
