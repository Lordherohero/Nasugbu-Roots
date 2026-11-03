class TouristSpot(
    val placeId: Int,
    var placeName: String,
    var description: String,
    var category: String,
    var allowsGuides: Boolean,
    var availability: Boolean,
    var imageUrl: String,
    val latitude: Double,
    val longitude: Double,
    val ownerId: Int,
    val verified: Boolean,
    var entryFee: Double
) : Searchable {

    init {
        require(placeId > 0) { "Place ID must be positive" }
        require(latitude in -90.0..90.0) { "Latitude must be between -90 and 90" }
        require(longitude in -180.0..180.0) { "Longitude must be between -180 and 180" }
        require(ownerId > 0) { "Owner ID must be positive" }
        require(entryFee >= 0.0) { "Entry fee cannot be negative" }
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