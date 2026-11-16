
package com.mygroup.nasugburoots.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Review(
    val reviewId: String = "",
    val userId: String = "",
    val placeId: Int = 0,
    val guideId: Int? = null,
    val reviewerName: String = "",
    val rating: Double = 0.0,
    val comment: String = "",

    @ServerTimestamp
    val createdAt: Timestamp? = null
) {
    fun isValidRating(): Boolean {
        return rating in 1.0..5.0
    }

    fun hasComment(): Boolean {
        return comment.isNotBlank()
    }
}
