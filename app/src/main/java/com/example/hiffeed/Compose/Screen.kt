package com.example.hiffeed.Compose

import com.example.hiffeed.R


object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "News",
            route = "news",
        ),
        BottomNavItem(
            label = "Forum",
            route = "ezine",

            ),
        BottomNavItem(
            label = "Stats",
            route = "stats",

            )

    )

    val TopNavItems = listOf(
        BottomNavItem(
            label = "Home",
            route = "home",
        ),
        BottomNavItem(
            label = "Players",
            route = "players",
            )

    )


}