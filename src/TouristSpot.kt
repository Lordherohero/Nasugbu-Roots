open class TouristSpot(
    protected var placeName: String,
    protected var description: String,
    protected var allowsGuides: Boolean,
    protected var availability: Boolean,
    protected var imageUrl: String,
    latitude: Double,
    longitude: Double,
    ownerId: Int,
    verified: Boolean
) {
    private val latitude: Double
    private val longitude: Double
    private val ownerId: Int
    private val verified: Boolean

    init {
        require(latitude in -90.0..90.0) { "Latitude must be between -90 and 90" }
        require(longitude in -180.0..180.0) { "Longitude must be between -180 and 180" }
        require(ownerId > 0) { "Owner ID must be positive" }

        this.latitude = latitude
        this.longitude = longitude
        this.ownerId = ownerId
        this.verified = verified
    }
    fun setPlaceName(placeName: String) {
        this.placeName = placeName
        println("Place name updated to: $placeName")
    }
    fun setDescription(description: String) {
        this.description = description
        println("Description updated to: $description")
    }
    fun setAllowsGuides(allowsGuides: Boolean) {
        this.allowsGuides = allowsGuides
        println("Allows guides updated to: $allowsGuides")
    }
    fun setAvailability(availability: Boolean) {
        this.availability = availability
        println("Availability updated to: $availability")
    }
    fun setImageUrl(imageUrl: String) {
        this.imageUrl = imageUrl
        println("Image URL updated to: $imageUrl")
    }
    // Getters
    fun getPlaceName() {
        println("Place Name: $placeName")
    }
    fun getDescription() {
        println("Description: $description")
    }
    fun getLocation() {
        println("Place Lat: $latitude")
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
        println("Verified: $verified")
    }
}