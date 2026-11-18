val users = mutableListOf<AppUser>()
val guides = mutableListOf<Guide>()
val spots = mutableListOf<TouristSpot>()
val bookings = mutableListOf<Booking>()
val reviews = mutableListOf<Review>()
var currentUserId = 0

fun loadSampleData() {
    val jun = Guide(2, "Jun", "jun@example.com", "Tagalog, English", 150.0, "Local guide", true)
    val ana = Guide(3, "Ana", "ana@example.com", "English, Filipino", 200.0, "Heritage tour guide", false)

    users.add(Tourist(1, "Maria", "maria@example.com"))
    users.add(jun)
    users.add(ana)
    users.add(SpotOwner(4, "Rosa", "rosa@example.com", "Owns the sample sites", true))
    users.add(Manager(5, "Leo", "leo@example.com", "App manager", true))
    users.add(Tourist(6, "Carlo", "carlo@example.com"))

    guides.add(jun)
    guides.add(ana)

    spots.add(TouristSpot(1, "Nasugbu Landing Monument", "An easy, zero-cost stop along Nasugbu's main beachfront, ideal for travelers who enjoy World War II history, coastal walks, or sunset photography.", "Historical Landmarks", true, true, "landing_monument.jpg", 14.0713, 120.6251, 4, true, 0.0, "63 Apacible Blvd., Nasugbu, Batangas"))
    spots.add(TouristSpot(2, "St. Francis Xavier Parish Church", "The main Roman Catholic church and designated Pilgrimage Church in the town proper under the Archdiocese of Lipa.", "Religious & Cultural Spots", true, true, "st_francis.jpg", 14.0747, 120.6322, 4, true, 0.0, "J.P. Laurel St., Brgy. Poblacion 2, Nasugbu, Batangas"))
    spots.add(TouristSpot(3, "Fortune Island", "A 27-hectare off-grid private island off the west coast of Nasugbu, famous for its cliffside Grecian Acropolis ruins, white sand beach, and turquoise waters.", "Beaches", true, true, "fortune_island.jpg", 14.0567, 120.4916, 4, true, 400.0, "Brgy. Wawa, Nasugbu, Batangas"))
    spots.add(TouristSpot(4, "Calayo Beach", "A laid-back, budget-friendly public beach and fishing village offering open shoreline access, fine golden-brown sand, calm shallow waters.", "Beaches", true, true, "calayo_beach.jpg", 14.1532, 120.6092, 4, true, 50.0, "Brgy. Calayo, Nasugbu, Batangas"))
    spots.add(TouristSpot(5, "Mount Batulao", "An inactive stratovolcano standing at 811 meters above sea level along the Nasugbu-Tagaytay ridge, famous for rolling cogon grass slopes and 360-degree views.", "Mountains & Nature", true, true, "mount_batulao.jpg", 14.0403, 120.8020, 4, true, 140.0, "Brgy. Kaylaway, Nasugbu, Batangas"))
}

fun findSpot(id: Int): TouristSpot? {
    return spots.find { spot -> spot.placeId == id }
}

fun findGuide(id: Int): Guide? {
    return guides.find { guide -> guide.getUserId() == id }
}

fun findBooking(id: Int): Booking? {
    return bookings.find { booking -> booking.bookingId == id }
}

fun spotReviews(placeId: Int): List<Review> {
    return reviews.filter { review -> review.placeId == placeId && review.guideId == null }
}

fun guideReviews(guideId: Int): List<Review> {
    return reviews.filter { review -> review.guideId == guideId }
}
