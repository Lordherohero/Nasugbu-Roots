fun verifySpot() {
    val unverified = mutableListOf<TouristSpot>()
    for (spot in spots) {
        if (!spot.getVerified()) {
            unverified.add(spot)
        }
    }
    if (unverified.isEmpty()) {
        println("All spots are already verified")
        return
    }
    showSpots(unverified)
    val spot = findSpot(readInt("Enter spot ID to verify: "))
    if (spot == null) {
        println("Spot not found")
        return
    }
    if (spot.getVerified()) {
        println("That spot is already verified")
        return
    }
    spot.setVerified(true)
}

fun verifyGuide() {
    val unverified = mutableListOf<Guide>()
    for (guide in guides) {
        if (!guide.getUserVerified()) {
            unverified.add(guide)
        }
    }
    if (unverified.isEmpty()) {
        println("All guides are already verified")
        return
    }
    showGuides(unverified)
    val guide = findGuide(readInt("Enter guide ID to verify: "))
    if (guide == null) {
        println("Guide not found")
        return
    }
    if (guide.getUserVerified()) {
        println("That guide is already verified")
        return
    }
    guide.setUserVerified(true)
}