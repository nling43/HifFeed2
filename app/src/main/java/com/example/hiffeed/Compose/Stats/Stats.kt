package com.example.hiffeed.Compose.Stats

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hiffeed.Compose.Constants
import com.example.hiffeed.database.Stats.PlayerViewModel
import com.example.hiffeed.ui.theme.HifFeedTheme


@Composable

fun stats(playerViewModel: PlayerViewModel) {
    val navController = rememberNavController()
    HifFeedTheme (isSystemInDarkTheme()){
        Surface(color = MaterialTheme.colors.background, ) {
            Scaffold(
                topBar = { TopNavigationBar(navController = navController) },
                containerColor = MaterialTheme.colors.background,
                content = { padding ->
                    NavHostContainer(
                        navController = navController,
                        playerViewModel = playerViewModel,
                        padding = padding,
                    )
                }

            )
        }
    }
}

@Composable
fun NavHostContainer(navController: NavHostController,
                     playerViewModel: PlayerViewModel,
                     padding: PaddingValues
) {
    NavHost(
        navController = navController,

        // set the start destination as home
        startDestination = "home",

        modifier = Modifier
            .padding(paddingValues = padding),

        builder = {

            composable("home") {
                Home()
            }

            composable("players") {
                Players(playerViewModel, navController)
            }

            composable("playerdetail") {
                PlayerDetails(playerViewModel)
            }



        })
}

@Composable

fun TopNavigationBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Constants.TopNavItems.forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)

                },
                icon = { Text(navItem.label) },


                )
        }
    }

}
