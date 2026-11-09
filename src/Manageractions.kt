fun verifySpot() {
    val unverified = spots.filter { spot -> !spot.verified }
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
    if (spot.verified) {
        println("That spot is already verified")
        return
    }
    spot.verified = true
    println("Verified updated to: true")
}

fun verifyGuide() {
    val unverified = guides.filter { guide -> !guide.getUserVerified() }
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