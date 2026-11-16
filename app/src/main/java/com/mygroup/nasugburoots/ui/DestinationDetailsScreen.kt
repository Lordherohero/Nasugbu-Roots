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
import com.mygroup.nasugburoots.model.Review
import com.mygroup.nasugburoots.model.TouristSpot
import com.mygroup.nasugburoots.ui.theme.Cream
import com.mygroup.nasugburoots.ui.theme.DeepForest
import com.mygroup.nasugburoots.ui.theme.Forest
import com.mygroup.nasugburoots.ui.theme.Gold
import com.mygroup.nasugburoots.ui.theme.Muted

@Composable
fun DestinationDetailsScreen(
    spot: TouristSpot,
    reviews: List<Review>,
    onBack: () -> Unit,
    onBookNow: (TouristSpot) -> Unit,
    onOpenMap: (TouristSpot) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
    ) {

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

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {

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

                Spacer(Modifier.height(20.dp))

                ReviewsSection(reviews = reviews)

                Spacer(Modifier.height(24.dp))
            }
        }

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