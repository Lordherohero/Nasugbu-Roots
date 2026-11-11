package com.mygroup.nasugburoots.data

import com.mygroup.nasugburoots.model.Booking
import com.mygroup.nasugburoots.model.BookingStatus
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

const val TEMP_TOURIST_ID = 1

fun isSpotBooked(bookings: List<Booking>, placeId: Int, date: String): Boolean {
    return bookings.any { booking ->
        booking.status != BookingStatus.CANCELLED && booking.placeId == placeId && booking.date == date
    }
}

fun isGuideBooked(bookings: List<Booking>, guideId: Int, date: String): Boolean {
    return bookings.any { booking ->
        booking.status != BookingStatus.CANCELLED && booking.guideId == guideId && booking.date == date
    }
}

fun todayUtcMillis(): Long {
    val local = Calendar.getInstance()
    val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    utc.clear()
    utc.set(local.get(Calendar.YEAR), local.get(Calendar.MONTH), local.get(Calendar.DAY_OF_MONTH))
    return utc.timeInMillis
}

fun formatDate(utcMillis: Long): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.format(Date(utcMillis))
}