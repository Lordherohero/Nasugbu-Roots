package com.mygroup.nasugburoots.model

data class Guide(
    val guideId: Int = 0,
    val name: String = "",
    val languages: String = "",
    val ratePerHour: Double = 0.0,
    val verified: Boolean = false
) {
    init {
        require(ratePerHour >= 0.0) { "Rate per hour cannot be negative" }
    }
}