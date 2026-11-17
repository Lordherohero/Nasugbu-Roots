package com.mygroup.nasugburoots.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mygroup.nasugburoots.data.averageRating
import com.mygroup.nasugburoots.model.Review
import com.mygroup.nasugburoots.ui.theme.DeepForest
import com.mygroup.nasugburoots.ui.theme.Forest
import com.mygroup.nasugburoots.ui.theme.Gold
import com.mygroup.nasugburoots.ui.theme.Muted
import java.util.Locale

@Composable
fun ReviewsSection(reviews: List<Review>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Reviews",
            color = DeepForest,
            fontFamily = FontFamily.Serif,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (reviews.isEmpty()) {
            Text(text = "No reviews yet.", color = Muted, fontSize = 13.sp)
        } else {
            val average = String.format(Locale.US, "%.1f", averageRating(reviews))
            Text(
                text = "$average out of 5 (${reviews.size} reviews)",
                color = Forest,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            reviews.forEach { review ->
                val name = if (review.reviewerName.isBlank()) "Traveler" else review.reviewerName

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = name,
                            color = DeepForest,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = stars(review.rating), color = Gold)
                        Text(text = review.comment, color = DeepForest, fontSize = 14.sp)
                        Text(text = review.date, color = Muted, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

private fun stars(rating: Double): String {
    val full = rating.toInt().coerceIn(0, 5)
    return "★".repeat(full) + "☆".repeat(5 - full)
}