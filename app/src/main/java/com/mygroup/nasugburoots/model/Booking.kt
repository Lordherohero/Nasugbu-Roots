package com.mygroup.nasugburoots.model

data class Booking(
    val bookingId: Int = 0,
    val placeId: Int = 0,
    val touristId: Int = 0,
    val guideId: Int? = null,
    val date: String = "",
    val hours: Int = 1,
    val guideRate: Double = 0.0,
    val placeFee: Double = 0.0,
    var status: BookingStatus = BookingStatus.PENDING
) {
    init {
        require(hours > 0) { "Hours must be positive" }
        require(guideRate >= 0.0) { "Guide rate cannot be negative" }
        require(placeFee >= 0.0) { "Place fee cannot be negative" }
    }

    fun totalCost(): Double {
        if (guideId != null) {
            return placeFee + guideRate * hours
        }
        return placeFee
    }

    fun confirmBooking() {
        if (status == BookingStatus.CANCELLED) {
            println("Cannot confirm a cancelled booking")
            return
        }
        status = BookingStatus.CONFIRMED
        println("Booking status updated to: ${status.label}")
    }

    fun cancelBooking() {
        status = BookingStatus.CANCELLED
        println("Booking status updated to: ${status.label}")
    }

    fun displayBooking() {
        println("Booking ID: $bookingId")
        println("Place ID: $placeId")
        println("Tourist ID: $touristId")
        if (guideId == null) {
            println("Guide ID: No guide")
        } else {
            println("Guide ID: $guideId")
        }
        println("Date: $date")
        println("Hours: $hours")
        println("Status: ${status.label}")
        println("Total Cost: ${totalCost()}")
    }
}