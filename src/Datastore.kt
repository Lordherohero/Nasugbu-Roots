import java.io.File

val usersFile = File("users.txt")
val spotsFile = File("spots.txt")
val bookingsFile = File("bookings.txt")
val reviewsFile = File("reviews.txt")

fun clean(text: String): String {
    return text.replace("|", "/")
}

fun saveAll() {
    val userLines = mutableListOf<String>()
    for (user in users) {
        var languages = ""
        var rate = 0.0
        if (user is Guide) {
            languages = user.getUserLanguages()
            rate = user.getRatePerHour()
        }
        userLines.add("${user.getUserId()}|${clean(user.getUserName())}|${clean(user.getUserEmail())}|${user.getUserRole().name}|${clean(user.getUserBio())}|${user.getUserVerified()}|${clean(languages)}|$rate")
    }
    usersFile.writeText(userLines.joinToString("\n"))

    spotsFile.writeText(spots.joinToString("\n") { spot ->
        "${spot.placeId}|${clean(spot.placeName)}|${clean(spot.description)}|${clean(spot.category)}|${spot.allowsGuides}|${spot.availability}|${clean(spot.imageUrl)}|${spot.latitude}|${spot.longitude}|${spot.ownerId}|${spot.verified}|${spot.entryFee}"
    })

    bookingsFile.writeText(bookings.joinToString("\n") { booking ->
        "${booking.bookingId}|${booking.placeId}|${booking.touristId}|${booking.guideId ?: "none"}|${booking.date}|${booking.hours}|${booking.guideRate}|${booking.placeFee}|${booking.status.name}"
    })

    reviewsFile.writeText(reviews.joinToString("\n") { review ->
        "${review.reviewId}|${review.userId}|${review.placeId}|${review.guideId ?: "none"}|${review.rating}|${clean(review.comment)}|${review.date}"
    })
}

fun loadAll(): Boolean {
    if (!usersFile.exists() || !spotsFile.exists() || !bookingsFile.exists() || !reviewsFile.exists()) {
        return false
    }

    try {
        for (line in usersFile.readLines()) {
            if (line.isBlank()) {
                continue
            }
            val p = line.split("|")
            val id = p[0].toInt()
            val role = Role.valueOf(p[3])
            val verified = p[5].toBoolean()

            if (role == Role.TOURIST) {
                users.add(Tourist(id, p[1], p[2], p[4], verified))
            } else if (role == Role.GUIDE) {
                val guide = Guide(id, p[1], p[2], p[6], p[7].toDouble(), p[4], verified)
                users.add(guide)
                guides.add(guide)
            } else if (role == Role.OWNER) {
                users.add(SpotOwner(id, p[1], p[2], p[4], verified))
            } else {
                users.add(Manager(id, p[1], p[2], p[4], verified))
            }
        }

        for (line in spotsFile.readLines()) {
            if (line.isBlank()) {
                continue
            }
            val p = line.split("|")
            spots.add(TouristSpot(p[0].toInt(), p[1], p[2], p[3], p[4].toBoolean(), p[5].toBoolean(), p[6], p[7].toDouble(), p[8].toDouble(), p[9].toInt(), p[10].toBoolean(), p[11].toDouble()))
        }

        for (line in bookingsFile.readLines()) {
            if (line.isBlank()) {
                continue
            }
            val p = line.split("|")
            var guideId: Int? = null
            if (p[3] != "none") {
                guideId = p[3].toInt()
            }
            bookings.add(Booking(p[0].toInt(), p[1].toInt(), p[2].toInt(), guideId, p[4], p[5].toInt(), p[6].toDouble(), p[7].toDouble(), BookingStatus.valueOf(p[8])))
        }

        for (line in reviewsFile.readLines()) {
            if (line.isBlank()) {
                continue
            }
            val p = line.split("|")
            var guideId: Int? = null
            if (p[3] != "none") {
                guideId = p[3].toInt()
            }
            reviews.add(Review(p[0].toInt(), p[1].toInt(), p[2].toInt(), guideId, p[4].toDouble(), p[5], p[6]))
        }
        return true
    } catch (e: Exception) {
        users.clear()
        guides.clear()
        spots.clear()
        bookings.clear()
        reviews.clear()
        println("Saved data could not be read. Starting with sample data")
        return false
    }
}