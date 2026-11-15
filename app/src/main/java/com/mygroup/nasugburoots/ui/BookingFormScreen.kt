package com.mygroup.nasugburoots.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mygroup.nasugburoots.data.TEMP_TOURIST_ID
import com.mygroup.nasugburoots.data.formatDate
import com.mygroup.nasugburoots.data.isGuideBooked
import com.mygroup.nasugburoots.data.isSpotBooked
import com.mygroup.nasugburoots.data.sampleGuides
import com.mygroup.nasugburoots.data.todayUtcMillis
import com.mygroup.nasugburoots.model.Booking
import com.mygroup.nasugburoots.model.BookingStatus
import com.mygroup.nasugburoots.model.Guide
import com.mygroup.nasugburoots.model.TouristSpot
import com.mygroup.nasugburoots.ui.theme.DeepForest
import com.mygroup.nasugburoots.ui.theme.Forest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingFormScreen(
    spot: TouristSpot,
    existingBookings: List<Booking>,
    nextId: Int,
    onBack: () -> Unit,
    onConfirm: (Booking) -> Unit
) {
    var date by remember { mutableStateOf("") }
    var hours by remember { mutableStateOf(1) }
    var selectedGuide by remember { mutableStateOf<Guide?>(null) }
    var showPicker by remember { mutableStateOf(false) }

    val pickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis >= todayUtcMillis()
            }
        }
    )

    val guide = selectedGuide
    val guideRate = if (guide != null) guide.ratePerHour else 0.0
    val total = spot.entryFee + guideRate * hours

    val problem = if (date.isBlank()) {
        ""
    } else if (isSpotBooked(existingBookings, spot.placeId, date)) {
        "This spot is already booked on that date. Choose another date."
    } else if (guide != null && isGuideBooked(existingBookings, guide.guideId, date)) {
        "That guide is already booked on that date. Choose another date or guide."
    } else {
        ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("Back")
        }

        Text(
            text = "Book a visit",
            color = Forest,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = spot.placeName,
            color = DeepForest,
            fontFamily = FontFamily.Serif,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Date", fontWeight = FontWeight.Bold)
        OutlinedButton(onClick = { showPicker = true }) {
            Text(if (date.isBlank()) "Choose a date" else date)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Hours", fontWeight = FontWeight.Bold)
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedButton(onClick = { if (hours > 1) hours = hours - 1 }) {
                Text("-")
            }
            Text(
                text = "  $hours  ",
                fontSize = 18.sp
            )
            OutlinedButton(onClick = { if (hours < 12) hours = hours + 1 }) {
                Text("+")
            }
        }

        if (spot.allowsGuides) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Guide (optional)", fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = guide == null,
                    onClick = { selectedGuide = null }
                )
                Text("No guide")
            }
            sampleGuides.forEach { option ->
                val label = if (option.verified) {
                    "${option.name} (verified)"
                } else {
                    option.name
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = guide == option,
                        onClick = { selectedGuide = option }
                    )
                    Column {
                        Text(text = label, fontWeight = FontWeight.Bold)
                        Text(
                            text = "${option.languages} | PHP ${option.ratePerHour} per hour",
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Total: PHP $total", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if (guide != null) {
            Text(
                text = "The guide has to confirm your request first.",
                fontSize = 12.sp
            )
        }

        if (problem.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = problem, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val booking = Booking(
                    bookingId = nextId,
                    placeId = spot.placeId,
                    touristId = TEMP_TOURIST_ID,
                    guideId = if (guide != null) guide.guideId else null,
                    date = date,
                    hours = hours,
                    guideRate = guideRate,
                    placeFee = spot.entryFee,
                    status = if (guide == null) BookingStatus.CONFIRMED else BookingStatus.PENDING
                )
                onConfirm(booking)
            },
            enabled = date.isNotBlank() && problem.isEmpty() && spot.availability,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (guide == null) "Confirm booking" else "Request booking")
        }
    }

    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val millis = pickerState.selectedDateMillis
                        if (millis != null) {
                            date = formatDate(millis)
                        }
                        showPicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = pickerState)
        }
    }
}