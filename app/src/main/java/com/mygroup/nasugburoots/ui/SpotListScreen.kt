package com.mygroup.nasugburoots.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mygroup.nasugburoots.model.TouristSpot

@Composable
fun SpotListScreen(
    spots: List<TouristSpot>,
    onSpotClick: (TouristSpot) -> Unit = {}
) {
    var query by remember { mutableStateOf("") }
    val shown = spots.filter { spot -> query.isBlank() || spot.searchByName(query) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search spots") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(shown) { spot -> SpotCard(spot, onClick = { onSpotClick(spot) }) }
        }
    }
}

@Composable
fun SpotCard(spot: TouristSpot, onClick: () -> Unit = {}) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = spot.placeName, style = MaterialTheme.typography.titleMedium)
            Text(text = spot.category, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Fee: PHP ${spot.entryFee}")
            if (spot.availability) {
                Text(text = "Available")
            } else {
                Text(text = "Not available")
            }
            if (spot.verified) {
                Text(text = "Verified")
            }
        }
    }
}