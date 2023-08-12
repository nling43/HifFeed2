package com.example.hiffeed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*



object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "News",
            route = "news",
            icon = R.drawable.news,
        ),
        BottomNavItem(
            label = "Ezine",
            route = "ezine",
            icon = R.drawable.forum,

            )
    )
}