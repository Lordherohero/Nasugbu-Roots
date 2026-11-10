package com.mygroup.nasugburoots.model

data class TouristSpot(
    val placeId: Int = 0,
    var placeName: String = "",
    var description: String = "",
    var category: String = "",
    var allowsGuides: Boolean = false,
    var availability: Boolean = true,
    var imageUrl: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val ownerId: Int = 0,
    var verified: Boolean = false,
    var entryFee: Double = 0.0
) : Searchable {

    init {
        require(latitude in -90.0..90.0) { "Latitude must be between -90 and 90" }
        require(longitude in -180.0..180.0) { "Longitude must be between -180 and 180" }
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