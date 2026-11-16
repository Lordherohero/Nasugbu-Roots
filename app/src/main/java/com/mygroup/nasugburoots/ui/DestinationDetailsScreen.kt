
package com.mygroup.nasugburoots.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Landscape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mygroup.nasugburoots.model.TouristSpot
import com.mygroup.nasugburoots.ui.theme.Cream
import com.mygroup.nasugburoots.ui.theme.DeepForest
import com.mygroup.nasugburoots.ui.theme.Forest
import com.mygroup.nasugburoots.ui.theme.Gold
import com.mygroup.nasugburoots.ui.theme.Muted
import com.mygroup.nasugburoots.ui.auth.AccountProfile
import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mygroup.nasugburoots.data.ReviewRepository
import com.mygroup.nasugburoots.model.Review
import com.mygroup.nasugburoots.ui.auth.AccountRole
import com.mygroup.nasugburoots.ui.auth.AccountStatus
import java.text.DateFormat

@Composable
fun DestinationDetailsScreen(
    spot: TouristSpot,
    userProfile: AccountProfile? = null,
    onBack: () -> Unit,
    onBookNow: (TouristSpot) -> Unit,
    onOpenMap: (TouristSpot) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {

        // Top navigation bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = "Go back",
                    tint = Forest
                )
            }

            Text(
                text = "Destination Details",
                color = DeepForest,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Scrollable destination information
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

            // Destination photo / placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(Forest),
                contentAlignment = Alignment.Center
            ) {
                if (spot.imageUrl.isNotBlank()) {
                    AsyncImage(
                        model = spot.imageUrl,
                        contentDescription = spot.placeName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFF38745A),
                                        DeepForest
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Landscape,
                            contentDescription = "Destination placeholder",
                            tint = Gold,
                            modifier = Modifier.size(90.dp)
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0xAA19362D)
                                )
                            )
                        )
                )

                Text(
                    text = spot.placeName,
                    color = Color.White,
                    fontSize = 29.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(22.dp)
                )
            }

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                // Category and verification
                Text(
                    text = spot.category.uppercase(),
                    color = Color(0xFFB57B30),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.3.sp
                )

                if (spot.verified) {
                    Surface(
                        modifier = Modifier.padding(top = 10.dp),
                        color = Color(0xFFE4EEE7),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                horizontal = 11.dp,
                                vertical = 7.dp
                            ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = Forest,
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(Modifier.width(6.dp))

                            Text(
                                "Verified Destination",
                                color = Forest,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Spacer(Modifier.height(18.dp))

                Text(
                    text = "About this place",
                    color = DeepForest,
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = spot.description.ifBlank {
                        "Destination information will be available soon."
                    },
                    color = Muted,
                    fontSize = 14.sp,
                    lineHeight = 22.sp
                )

                Spacer(Modifier.height(24.dp))

                // Entrance fee
                InformationCard(
                    title = "Entrance Fee",
                    value = if (spot.entryFee > 0) {
                        "₱${spot.entryFee}"
                    } else {
                        "No fee listed"
                    },
                    icon = Icons.Outlined.Payments
                )

                Spacer(Modifier.height(12.dp))

                // Availability
                InformationCard(
                    title = "Availability",
                    value = if (spot.availability) {
                        "Currently Available"
                    } else {
                        "Currently Unavailable"
                    },
                    icon = if (spot.availability) {
                        Icons.Outlined.CheckCircle
                    } else {
                        Icons.Outlined.Schedule
                    }
                )

                Spacer(Modifier.height(12.dp))

                // Tour guide availability
                InformationCard(
                    title = "Tour Guide",
                    value = if (spot.allowsGuides) {
                        "Guides are allowed"
                    } else {
                        "Guides are not listed as available"
                    },
                    icon = Icons.Outlined.Person
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Location",
                    color = DeepForest,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )

                Spacer(Modifier.height(10.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.LocationOn,
                                contentDescription = null,
                                tint = Forest
                            )

                            Spacer(Modifier.width(8.dp))

                            Text(
                                "Destination coordinates",
                                color = DeepForest,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            "${spot.latitude}, ${spot.longitude}",
                            color = Muted,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(
                                start = 32.dp,
                                top = 5.dp
                            )
                        )

                        OutlinedButton(
                            onClick = {
                                onOpenMap(spot)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 14.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Map,
                                contentDescription = null
                            )

                            Spacer(Modifier.width(7.dp))

                            Text("View on Map")
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Destination Reviews
                DestinationReviewsSection(
                    spot = spot,
                    userProfile = userProfile
                )

                Spacer(Modifier.height(24.dp))
            }
        }

        // Fixed booking button at the bottom
        Surface(
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Button(
                onClick = {
                    onBookNow(spot)
                },
                enabled = spot.availability,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 14.dp
                    )
                    .height(54.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Forest,
                    contentColor = Color.White,
                    disabledContainerColor = Muted
                )
            ) {
                Text(
                    if (spot.availability) {
                        "Book This Destination"
                    } else {
                        "Currently Unavailable"
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun InformationCard(
    title: String,
    value: String,
    icon: ImageVector
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Forest,
                modifier = Modifier.size(25.dp)
            )

            Column(
                modifier = Modifier.padding(start = 13.dp)
            ) {
                Text(
                    title,
                    color = Muted,
                    fontSize = 11.sp
                )

                Text(
                    value,
                    color = DeepForest,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
private fun DestinationReviewsSection(
    spot: TouristSpot,
    userProfile: AccountProfile?
) {
    val reviews = remember(spot.placeId) {
        mutableStateListOf<Review>()
    }

    val rating = remember { mutableStateOf(0) }
    val comment = remember { mutableStateOf("") }
    val isSubmitting = remember { mutableStateOf(false) }
    val message = remember { mutableStateOf("") }

    val canReview = userProfile?.let {
        it.role == AccountRole.TOURIST &&
                it.status == AccountStatus.ACTIVE
    } == true

    // Load reviews when the destination opens
    LaunchedEffect(spot.placeId) {
        ReviewRepository.getReviewsForPlace(spot.placeId) { result ->
            result.onSuccess { loadedReviews ->
                reviews.clear()
                reviews.addAll(loadedReviews)
            }
        }
    }

    val averageRating = if (reviews.isEmpty()) {
        0.0
    } else {
        reviews.map { it.rating }.average()
    }

    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = "Reviews & Ratings",
            color = DeepForest,
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = if (reviews.isEmpty()) {
                "No reviews yet. Be the first to share your experience!"
            } else {
                "★ ${String.format("%.1f", averageRating)} / 5.0  •  ${reviews.size} reviews"
            },
            color = Gold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))

        // Review submission form for active tourists
        if (canReview) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text(
                        text = "Share your experience",
                        color = DeepForest,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Spacer(Modifier.height(10.dp))

                    Text(
                        text = "Your rating",
                        color = Muted,
                        fontSize = 13.sp
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        (1..5).forEach { star ->
                            Text(
                                text = if (star <= rating.value) "★" else "☆",
                                color = Gold,
                                fontSize = 32.sp,
                                modifier = Modifier
                                    .clickable { rating.value = star }
                                    .padding(end = 5.dp)
                            )
                        }
                    }

                    OutlinedTextField(
                        value = comment.value,
                        onValueChange = { comment.value = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Write your review") },
                        placeholder = {
                            Text("Tell others about your experience...")
                        },
                        minLines = 3,
                        maxLines = 5,
                        shape = RoundedCornerShape(12.dp)
                    )

                    if (message.value.isNotBlank()) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = message.value,
                            color = Forest,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = {
                            val profile = userProfile

                            isSubmitting.value = true
                            message.value = ""

                            val newReview = Review(
                                userId = profile.userId,
                                placeId = spot.placeId,
                                reviewerName = profile.fullName,
                                rating = rating.value.toDouble(),
                                comment = comment.value.trim()
                            )

                            ReviewRepository.submitReview(newReview) { result ->
                                isSubmitting.value = false

                                result.onSuccess {
                                    comment.value = ""
                                    rating.value = 0
                                    message.value = "Your review was submitted!"

                                    ReviewRepository.getReviewsForPlace(
                                        spot.placeId
                                    ) { refreshResult ->
                                        refreshResult.onSuccess { updated ->
                                            reviews.clear()
                                            reviews.addAll(updated)
                                        }
                                    }
                                }

                                result.onFailure { error ->
                                    message.value =
                                        error.message ?: "Unable to submit review."
                                }
                            }
                        },
                        enabled = rating.value > 0 &&
                                comment.value.isNotBlank() &&
                                !isSubmitting.value,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Forest
                        )
                    ) {
                        Text(
                            if (isSubmitting.value) {
                                "Submitting..."
                            } else {
                                "Submit Review"
                            }
                        )
                    }
                }
            }
        } else if (userProfile == null) {
            Text(
                text = "Log in as a tourist to submit a review.",
                color = Muted,
                fontSize = 13.sp
            )
        }

        Spacer(Modifier.height(18.dp))

        // Display submitted reviews
        if (reviews.isEmpty()) {
            Text(
                text = "Reviews will appear here.",
                color = Muted,
                fontSize = 13.sp
            )
        } else {
            reviews.forEach { review ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(14.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(15.dp)) {

                        Text(
                            text = review.reviewerName,
                            color = DeepForest,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = "★ ${review.rating} / 5",
                            color = Gold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            text = review.comment,
                            color = Muted,
                            fontSize = 13.sp,
                            lineHeight = 20.sp
                        )

                        review.createdAt?.let { timestamp ->
                            Spacer(Modifier.height(6.dp))
                            Text(
                                text = DateFormat.getDateInstance(
                                    DateFormat.MEDIUM
                                ).format(timestamp.toDate()),
                                color = Muted,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
