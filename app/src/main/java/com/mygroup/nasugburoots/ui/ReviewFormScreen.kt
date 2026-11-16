package com.mygroup.nasugburoots.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.mygroup.nasugburoots.data.CurrentUser
import com.mygroup.nasugburoots.data.todayLocal
import com.mygroup.nasugburoots.model.Booking
import com.mygroup.nasugburoots.model.Review
import com.mygroup.nasugburoots.ui.theme.Cream
import com.mygroup.nasugburoots.ui.theme.DeepForest
import com.mygroup.nasugburoots.ui.theme.Forest
import com.mygroup.nasugburoots.ui.theme.Gold

@Composable
fun ReviewFormScreen(
    booking: Booking,
    spotName: String,
    guideName: String?,
    onBack: () -> Unit,
    onSubmit: (Review, Review?) -> Unit
) {
    var spotRating by remember { mutableStateOf(0) }
    var spotComment by remember { mutableStateOf("") }
    var rateGuide by remember { mutableStateOf(false) }
    var guideRating by remember { mutableStateOf(0) }
    var guideComment by remember { mutableStateOf("") }

    val spotReady = spotRating >= 1 && spotComment.isNotBlank()
    val guideReady = !rateGuide || (guideRating >= 1 && guideComment.isNotBlank())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TextButton(onClick = onBack) {
            Text("Back")
        }

        Text(
            text = "Write a review",
            color = Forest,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = spotName,
            color = DeepForest,
            fontFamily = FontFamily.Serif,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Your rating", fontWeight = FontWeight.Bold)
        StarRating(rating = spotRating, onChange = { spotRating = it })

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = spotComment,
            onValueChange = { spotComment = it },
            label = { Text("Your comment") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        if (guideName != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = rateGuide,
                    onCheckedChange = { rateGuide = it }
                )
                Text(text = "Also rate your guide, $guideName")
            }

            if (rateGuide) {
                StarRating(rating = guideRating, onChange = { guideRating = it })
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = guideComment,
                    onValueChange = { guideComment = it },
                    label = { Text("Comment about $guideName") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val date = todayLocal()
                val spotReview = Review(
                    userId = CurrentUser.id,
                    placeId = booking.placeId,
                    guideId = null,
                    rating = spotRating.toDouble(),
                    comment = spotComment.trim(),
                    date = date
                )
                val guideReview = if (rateGuide) {
                    Review(
                        userId = CurrentUser.id,
                        placeId = booking.placeId,
                        guideId = booking.guideId,
                        rating = guideRating.toDouble(),
                        comment = guideComment.trim(),
                        date = date
                    )
                } else {
                    null
                }
                onSubmit(spotReview, guideReview)
            },
            enabled = spotReady && guideReady,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit review")
        }
    }
}

@Composable
private fun StarRating(rating: Int, onChange: (Int) -> Unit) {
    Row {
        for (value in 1..5) {
            Text(
                text = if (value <= rating) "★" else "☆",
                color = Gold,
                fontSize = 36.sp,
                modifier = Modifier
                    .clickable { onChange(value) }
                    .padding(end = 4.dp)
            )
        }
    }
}