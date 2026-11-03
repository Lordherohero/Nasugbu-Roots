class TouristSpot(
    private val placeId: Int,
    private var placeName: String,
    private var description: String,
    private var category: String,
    private var allowsGuides: Boolean,
    private var availability: Boolean,
    private var imageUrl: String,
    private val latitude: Double,
    private val longitude: Double,
    private val ownerId: Int,
    private val verified: Boolean,
    private var entryFee: Double
) : Searchable {

    init {
        require(placeId > 0) { "Place ID must be positive" }
        require(latitude in -90.0..90.0) { "Latitude must be between -90 and 90" }
        require(longitude in -180.0..180.0) { "Longitude must be between -180 and 180" }
        require(ownerId > 0) { "Owner ID must be positive" }
        require(entryFee >= 0.0) { "Entry fee cannot be negative" }
    }

    fun getPlaceId(): Int {
        return placeId
    }

    fun getPlaceName(): String {
        return placeName
    }

    fun getDescription(): String {
        return description
    }

    fun getCategory(): String {
        return category
    }

    fun getAllowsGuides(): Boolean {
        return allowsGuides
    }

    fun getAvailability(): Boolean {
        return availability
    }

    fun getImageUrl(): String {
        return imageUrl
    }

    fun getLatitude(): Double {
        return latitude
    }

    fun getLongitude(): Double {
        return longitude
    }

    fun getOwnerId(): Int {
        return ownerId
    }

    fun getVerified(): Boolean {
        return verified
    }

    fun getEntryFee(): Double {
        return entryFee
    }

    fun setPlaceName(placeName: String) {
        this.placeName = placeName
        println("Place name updated to: $placeName")
    }

    fun setDescription(description: String) {
        this.description = description
        println("Description updated to: $description")
    }

    fun setCategory(category: String) {
        this.category = category
        println("Category updated to: $category")
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

    fun setEntryFee(entryFee: Double) {
        require(entryFee >= 0.0) { "Entry fee cannot be negative" }
        this.entryFee = entryFee
        println("Entry fee updated to: $entryFee")
    }

    override fun searchByName(name: String): Boolean {
        return placeName.contains(name.trim(), ignoreCase = true)
    }

    override fun searchByCategory(category: String): Boolean {
        return this.category.equals(category.trim(), ignoreCase = true)
    }

    fun displaySpot() {
        println("Place ID: $placeId")
        println("Place Name: $placeName")
        println("Description: $description")
        println("Category: $category")
        println("Allows Guides: $allowsGuides")
        println("Availability: $availability")
        println("Image Url: $imageUrl")
        println("Location: $latitude, $longitude")
        println("Owner ID: $ownerId")
        println("Verified: $verified")
        println("Entry Fee: $entryFee")
    }
}