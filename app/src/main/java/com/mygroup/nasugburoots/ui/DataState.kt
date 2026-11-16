package com.mygroup.nasugburoots.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mygroup.nasugburoots.data.Repository
import com.mygroup.nasugburoots.model.Booking
import com.mygroup.nasugburoots.model.Guide
import com.mygroup.nasugburoots.model.TouristSpot

@Composable
fun rememberSpots(): List<TouristSpot> {
    var spots by remember { mutableStateOf(emptyList<TouristSpot>()) }

    LaunchedEffect(Unit) {
        Repository.seedIfEmpty()
    }

    DisposableEffect(Unit) {
        val registration = Repository.listenToSpots { spots = it }
        onDispose { registration.remove() }
    }

    return spots
}

@Composable
fun rememberGuides(): List<Guide> {
    var guides by remember { mutableStateOf(emptyList<Guide>()) }

    DisposableEffect(Unit) {
        val registration = Repository.listenToGuides { guides = it }
        onDispose { registration.remove() }
    }

    return guides
}

@Composable
fun rememberBookings(): List<Booking> {
    var bookings by remember { mutableStateOf(emptyList<Booking>()) }

    DisposableEffect(Unit) {
        val registration = Repository.listenToBookings { bookings = it }
        onDispose { registration.remove() }
    }

    return bookings
}