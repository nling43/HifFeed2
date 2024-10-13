package com.example.hiffeed.Compose


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hiffeed.Compose.Stats.stats
import com.example.hiffeed.database.MessageAndNews.Message.ForumViewModel
import com.example.hiffeed.database.MessageAndNews.News.NewsViewModel
import com.example.hiffeed.database.Stats.PlayerViewModel
import com.example.hiffeed.ui.theme.HifFeedTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        val newsViewModel: NewsViewModel by viewModels()
        val forumViewModel: ForumViewModel by viewModels()
        val playerViewModel: PlayerViewModel by viewModels()
        newsViewModel.update()
        forumViewModel.update()
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

                    Surface(color = MaterialTheme.colors.background) {
                        Scaffold(
                            bottomBar = { BottomNavigationBar(navController = navController) },
                            content = { padding ->
                                NavHostContainer(
                                    navController = navController,
                                    newsViewModel = newsViewModel,
                                    forumViewModel = forumViewModel,
                                    playerViewModel =playerViewModel,
                                    padding = padding
                                )
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
        newsViewModel: NewsViewModel,
        playerViewModel:PlayerViewModel,
        forumViewModel: ForumViewModel,
        padding: PaddingValues

    ) {

        NavHost(
            navController = navController,

            // set the start destination as home
            startDestination = "news",

            modifier = Modifier
                .padding(paddingValues = padding)
                .background(MaterialTheme.colors.background),
            builder = {

                composable("news") {
                    NewsScreen(newsViewModel)
                }

                composable("ezine") {
                    ezine(forumViewModel)
                }

                composable("stats") {
                    stats(playerViewModel)
                }

            })

    }

    @Composable
    fun BottomNavigationBar(navController: NavHostController) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            Constants.BottomNavItems.forEach { navItem ->
                BottomNavigationItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route)

                    },
                    icon= {Text(navItem.label) },


                )
            }
        }

    }
}









