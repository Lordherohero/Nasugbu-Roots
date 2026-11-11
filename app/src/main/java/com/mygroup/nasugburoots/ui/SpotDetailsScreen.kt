package com.mygroup.nasugburoots.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mygroup.nasugburoots.model.TouristSpot
import com.mygroup.nasugburoots.ui.theme.DeepForest
import com.mygroup.nasugburoots.ui.theme.Forest
import com.mygroup.nasugburoots.ui.theme.Muted

@Composable
fun SpotDetailsScreen(
    spot: TouristSpot,
    onBack: () -> Unit,
    onBook: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("Back to list")
        }

        Text(
            text = spot.category.uppercase(),
            color = Forest,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = spot.placeName,
            color = DeepForest,
            fontFamily = FontFamily.Serif,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(text = spot.description, color = Muted, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(12.dp))
        if (spot.entryFee > 0) {
            Text(text = "Entry fee: PHP ${spot.entryFee}", fontSize = 14.sp)
        } else {
            Text(text = "No entry fee listed", fontSize = 14.sp)
        }
        if (spot.availability) {
            Text(text = "Open for visits", fontSize = 14.sp)
        } else {
            Text(text = "Currently not available", fontSize = 14.sp)
        }
        if (spot.allowsGuides) {
            Text(text = "Local guides allowed", fontSize = 14.sp)
        }
        if (spot.verified) {
            Text(text = "Verified by a manager", fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))
        SpotMap(
            latitude = spot.latitude,
            longitude = spot.longitude,
            title = spot.placeName,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .heightIn(min = 180.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onBook,
            enabled = spot.availability,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (spot.availability) {
                Text("Book a visit")
            } else {
                Text("Not available for booking")
            }
        }
    }
}