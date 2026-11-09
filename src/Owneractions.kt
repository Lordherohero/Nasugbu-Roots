fun chooseOwnSpot(): TouristSpot? {
    val mine = spots.filter { spot -> spot.ownerId == currentUserId }
    if (mine.isEmpty()) {
        println("You have no spots yet")
        return null
    }
    showSpots(mine)
    val spot = findSpot(readInt("Enter spot ID: "))
    if (spot == null) {
        println("Spot not found")
        return null
    }
    if (spot.ownerId != currentUserId) {
        println("That spot is not yours")
        return null
    }
    return spot
}

fun showMySpots() {
    val mine = spots.filter { spot -> spot.ownerId == currentUserId }
    if (mine.isEmpty()) {
        println("You have no spots yet")
        return
    }
    showSpots(mine)
}

fun addSpot() {
    val name = readText("Place name: ")
    if (name.isBlank()) {
        println("Place name cannot be blank")
        return
    }
    val description = readText("Description: ")
    val category = readText("Category: ")
    val allowsGuides = readText("Allow guides? (y/n): ").equals("y", ignoreCase = true)
    val fee = readDouble("Entry fee: ")
    if (fee < 0.0) {
        println("Entry fee cannot be negative")
        return
    }
    val imageUrl = readText("Image file name or URL: ")
    val latitude = readDouble("Latitude: ")
    if (latitude < -90.0 || latitude > 90.0) {
        println("Latitude must be between -90 and 90")
        return
    }
    val longitude = readDouble("Longitude: ")
    if (longitude < -180.0 || longitude > 180.0) {
        println("Longitude must be between -180 and 180")
        return
    }

    val spot = TouristSpot(spots.size + 1, name, description, category, allowsGuides, true, imageUrl, latitude, longitude, currentUserId, false, fee)
    spots.add(spot)
    println("Spot added. A manager needs to verify it")
}

fun editSpotDetails() {
    val spot = chooseOwnSpot()
    if (spot == null) {
        return
    }
    println("1. Name")
    println("2. Category")
    println("3. Description")
    println("4. Image")
    val choice = readText("What do you want to change? ")
    if (choice != "1" && choice != "2" && choice != "3" && choice != "4") {
        println("Invalid choice")
        return
    }
    val value = readText("New value: ")
    if (value.isBlank()) {
        println("Value cannot be blank")
        return
    }
    when (choice) {
        "1" -> {
            spot.placeName = value
            println("Place name updated to: $value")
        }
        "2" -> {
            spot.category = value
            println("Category updated to: $value")
        }
        "3" -> {
            spot.description = value
            println("Description updated to: $value")
        }
        "4" -> {
            spot.imageUrl = value
            println("Image updated to: $value")
        }
    }
}

fun changeEntryFee() {
    val spot = chooseOwnSpot()
    if (spot == null) {
        return
    }
    val fee = readDouble("New entry fee: ")
    if (fee < 0.0) {
        println("Entry fee cannot be negative")
        return
    }
    spot.entryFee = fee
    println("Entry fee updated to: $fee")
}

fun changeAvailability() {
    val spot = chooseOwnSpot()
    if (spot == null) {
        return
    }
    spot.availability = !spot.availability
    println("Availability updated to: ${spot.availability}")
}

fun changeAllowsGuides() {
    val spot = chooseOwnSpot()
    if (spot == null) {
        return
    }
    spot.allowsGuides = !spot.allowsGuides
    println("Allows guides updated to: ${spot.allowsGuides}")
}