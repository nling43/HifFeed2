package com.example.hiffeed


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hiffeed.Compose.NewsScreen
import com.example.hiffeed.Compose.ezine
import com.example.hiffeed.database.ViewModel
import com.example.hiffeed.ui.theme.HifFeedTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: ViewModel by viewModels()
        viewModel.update()


        super.onCreate(savedInstanceState)
        setContent {
            HifFeedTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    // remember navController so it does not
                    // get recreated on recomposition
                    val navController = rememberNavController()

                    Surface(color = Color.White) {
                        Scaffold(
                            bottomBar = {
                                BottomNavigationBar(navController = navController)
                            }, content = { padding ->
                                NavHostContainer(navController = navController, viewModel = viewModel, padding)
                            }
                        )

                    }
                }
            }
        }


    }

    @Composable
    fun NavHostContainer(
        navController: NavHostController,
        viewModel: ViewModel,
        padding: PaddingValues
    ) {

        NavHost(
            navController = navController,

            // set the start destination as home
            startDestination = "news",

            modifier = Modifier.padding(paddingValues = padding),
            builder = {

                composable("news") {
                    NewsScreen(viewModel)
                }

                composable("ezine") {
                    ezine()
                }

            })

    }
    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            Constants.BottomNavItems.forEach { navItem ->
                BottomNavigationItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route)
                    },
                    icon = {
                        Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                    },
                    label = {
                        Text(text = navItem.label)
                    },
                    alwaysShowLabel = false
                )
            }
        }

    }
}









