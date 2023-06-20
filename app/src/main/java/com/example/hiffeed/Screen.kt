package com.example.hiffeed

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*



object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "News",
            route = "news",
                    icon = Icons.Default.Home,
        ),
        BottomNavItem(
            label = "Ezine",
            route = "ezine",
                    icon = Icons.Filled.Home,

            )
    )
}