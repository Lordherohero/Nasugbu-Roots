package com.mygroup.nasugburoots.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mygroup.nasugburoots.model.Booking
import com.mygroup.nasugburoots.model.BookingStatus
import com.mygroup.nasugburoots.model.Guide
import com.mygroup.nasugburoots.model.TouristSpot
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.LaunchedEffect

private enum class VisitFilter(val label: String) {
    UPCOMING("Upcoming"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled")
}

@Composable
fun MyBookingsScreen(
    bookings: List<Booking>,
    spots: List<TouristSpot>,
    guides: List<Guide>,
    onCancel: (Int) -> Unit
) {
    var selectedFilter by remember {
        mutableStateOf(VisitFilter.UPCOMING)
    }

    val today = remember {
        SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    }

    LaunchedEffect(bookings, today) {
        bookings
            .filter { booking ->
                booking.status == BookingStatus.PENDING &&
                        booking.date.isNotBlank() &&
                        booking.date < today
            }
            .forEach { booking ->
                onCancel(booking.bookingId)
            }
    }
    // Completed means confirmed and scheduled before today.
    fun isCompleted(booking: Booking): Boolean {
        return booking.status == BookingStatus.CONFIRMED &&
                booking.date.isNotBlank() &&
                booking.date < today
    }

    val upcomingBookings = bookings.filter { booking ->
        booking.status != BookingStatus.CANCELLED &&
                !isCompleted(booking)
    }

    val completedBookings = bookings.filter { booking ->
        isCompleted(booking)
    }

    val cancelledBookings = bookings.filter { booking ->
        booking.status == BookingStatus.CANCELLED
    }

    val filteredBookings = when (selectedFilter) {
        VisitFilter.UPCOMING -> upcomingBookings
        VisitFilter.COMPLETED -> completedBookings
        VisitFilter.CANCELLED -> cancelledBookings
    }.sortedBy { it.date }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "My Visits",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Manage your heritage site visits and bookings.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Visit counts
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            VisitCountCard(
                title = "Upcoming",
                count = upcomingBookings.size,
                modifier = Modifier.weight(1f)
            )

            VisitCountCard(
                title = "Completed",
                count = completedBookings.size,
                modifier = Modifier.weight(1f)
            )

            VisitCountCard(
                title = "Cancelled",
                count = cancelledBookings.size,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            VisitFilter.entries.forEach { filter ->
                val count = when (filter) {
                    VisitFilter.UPCOMING -> upcomingBookings.size
                    VisitFilter.COMPLETED -> completedBookings.size
                    VisitFilter.CANCELLED -> cancelledBookings.size
                }

                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { selectedFilter = filter },
                    label = {
                        Text("${filter.label} ($count)")
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (filteredBookings.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = when (selectedFilter) {
                        VisitFilter.UPCOMING -> "No upcoming visits"
                        VisitFilter.COMPLETED -> "No completed visits yet"
                        VisitFilter.CANCELLED -> "No cancelled bookings"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = when (selectedFilter) {
                        VisitFilter.UPCOMING ->
                            "Explore Nasugbu heritage sites and book your next visit."

                        VisitFilter.COMPLETED ->
                            "Your completed visits will appear here."

                        VisitFilter.CANCELLED ->
                            "Cancelled bookings will appear here."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredBookings) { booking ->
                    BookingCard(
                        booking = booking,
                        spots = spots,
                        guides = guides,
                        isCompleted = isCompleted(booking),
                        onCancel = onCancel
                    )
                }
            }
        }
    }
}

@Composable
private fun VisitCountCard(
    title: String,
    count: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

@Composable
private fun BookingCard(
    booking: Booking,
    spots: List<TouristSpot>,
    guides: List<Guide>,
    isCompleted: Boolean,
    onCancel: (Int) -> Unit
) {
    val spot = spots.find { it.placeId == booking.placeId }
    val spotName = spot?.placeName ?: "Unknown spot"

    val guide = guides.find {
        it.guideId == booking.guideId
    }

    val guideName = guide?.name ?: "No guide"

    val displayStatus = when {
        booking.status == BookingStatus.CANCELLED -> "Cancelled"
        isCompleted -> "Completed"
        booking.status == BookingStatus.PENDING -> "Pending confirmation"
        else -> "Confirmed"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = spotName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = "Visit date: ${booking.date}")
            Text(text = "Duration: ${booking.hours} hour(s)")
            Text(text = "Guide: $guideName")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Status: $displayStatus",
                fontWeight = FontWeight.SemiBold,
                color = when {
                    booking.status == BookingStatus.CANCELLED ->
                        MaterialTheme.colorScheme.error

                    isCompleted ->
                        MaterialTheme.colorScheme.primary

                    else ->
                        MaterialTheme.colorScheme.secondary
                }
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Total: PHP ${booking.totalCost()}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            if (
                booking.status != BookingStatus.CANCELLED &&
                !isCompleted
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        onCancel(booking.bookingId)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel booking")
                }
            }
        }
    }
}