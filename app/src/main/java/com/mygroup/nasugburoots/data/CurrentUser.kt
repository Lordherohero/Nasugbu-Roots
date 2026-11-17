package com.mygroup.nasugburoots.data

object CurrentUser {
    private const val DEMO_ID = "demo-tourist"

    var id: String = DEMO_ID

    fun reset() {
        id = DEMO_ID
    }
}