package com.mygroup.nasugburoots

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mygroup.nasugburoots.data.sampleSpots
import com.mygroup.nasugburoots.ui.SpotListScreen
import com.mygroup.nasugburoots.ui.theme.NasubuRootsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NasubuRootsTheme {
                SpotListScreen(sampleSpots)
            }
        }
    }
}