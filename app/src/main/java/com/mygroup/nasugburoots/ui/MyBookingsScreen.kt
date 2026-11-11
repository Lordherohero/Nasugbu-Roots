package com.mygroup.nasugburoots.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mygroup.nasugburoots.data.sampleGuides
import com.mygroup.nasugburoots.model.Booking
import com.mygroup.nasugburoots.model.BookingStatus
import com.mygroup.nasugburoots.model.TouristSpot

@Composable
fun MyBookingsScreen(
    bookings: List<Booking>,
    spots: List<TouristSpot>,
    onCancel: (Int) -> Unit
) {
    if (bookings.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "No bookings yet", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Open a destination and tap Book a visit.")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(bookings) { booking ->
                BookingCard(booking, spots, onCancel)
            }
        }
    }
}

@Composable
private fun BookingCard(
    booking: Booking,
    spots: List<TouristSpot>,
    onCancel: (Int) -> Unit
) {
    val spot = spots.find { item -> item.placeId == booking.placeId }
    val spotName = if (spot != null) spot.placeName else "Unknown spot"
    val guide = sampleGuides.find { item -> item.guideId == booking.guideId }
    val guideName = if (guide != null) guide.name else "No guide"

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = spotName, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Date: ${booking.date}")
            Text(text = "Hours: ${booking.hours}")
            Text(text = "Guide: $guideName")
            Text(text = "Status: ${booking.status.label}")
            Text(text = "Total: PHP ${booking.totalCost()}")

            if (booking.status != BookingStatus.CANCELLED) {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = { onCancel(booking.bookingId) }) {
                    Text("Cancel booking")
                }
            }
        }
    }
}