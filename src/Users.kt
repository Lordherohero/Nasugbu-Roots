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
)

class GuideProfile(
    val bio: String = "",
    val languages: String = "",
    val ratePerDay: Double = 0.0
)

class Booking(
    val spotId: String = "",
    val travelerId: String = "",
    val guideId: String = "",
    val status: String = ""
)
// functions planed to be added  (feel free to add any ideas po)
// displayLocation(), showStatus(), verifyUser(), markLoaction(), findLocation()....