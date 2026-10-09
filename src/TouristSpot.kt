abstract class TouristSpot(
    protected var placeName: String,
    protected var description: String,
    private val latitude: Double,
    private val longitude: Double,
    private val ownerId: Int,
    protected var allowsGuides: Boolean,
    protected var availability: Boolean,
    protected var imageUrl: String,
    private val verified: Boolean
){
    abstract fun setPlaceName(): String
    abstract fun setDescription(): String
    abstract fun setLatitude(): Double
    abstract fun setLongitude(): Double
    abstract fun setOwnerId(): Int
    abstract fun setAllowsGuides(): Boolean
    abstract fun setAvailability(): Boolean
    abstract fun setImageUrl(): String
    abstract fun setVerified(): Boolean

    fun getPlaceName() {
        println("Place Name: $placeName")
    }
    fun getDescription() {
        println("Place Name: $description")
    }
    fun getLatitude() {
        println("Place Lat: $latitude")
    }
    fun getLongitude() {
        println("Place Long: $longitude")
    }
    fun getOwnerId() {
        println("Owner ID: $ownerId")
    }
    fun getAllowsGuides() {
        println("Allows Guides: $allowsGuides")
    }
    fun getAvailability() {
        println("Availability: $availability")
    }
    fun getImageUrl() {
        println("Image Url: $imageUrl")
    }
    fun getVerified() {
        println("Verified : $verified")
    }
}