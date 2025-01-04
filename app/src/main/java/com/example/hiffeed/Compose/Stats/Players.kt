package com.example.hiffeed.Compose.Stats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.hiffeed.Compose.LoadingItem
import com.example.hiffeed.FotballAPI.PlayerDetail
import com.example.hiffeed.database.Stats.PlayerItem
import com.example.hiffeed.database.Stats.PlayerViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
var viewmodel: PlayerViewModel? = null
@Composable
fun Players(playersViewmodel: PlayerViewModel, navController: NavHostController) {
    viewmodel = playersViewmodel
    val players: List<PlayerItem> by viewmodel!!.players.observeAsState(emptyList())
    val goalkeepers: List<PlayerItem> by viewmodel!!.goalkeepers.observeAsState(emptyList())
    val defenders: List<PlayerItem> by viewmodel!!.defenders.observeAsState(emptyList())
    val attackers: List<PlayerItem> by viewmodel!!.attackers.observeAsState(emptyList())
    val midfielders: List<PlayerItem> by viewmodel!!.midfielders.observeAsState(emptyList())
    val isRefreshing by viewmodel!!.isRefreshing.collectAsState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewmodel!!.refresh() },
    ) {
        if (isRefreshing) {
            LoadingItem()
        } else {
            LazyColumn {
                items(4) { index ->
                    when (index) {
                        0 -> PossitionCard("Goalkeepers", goalkeepers,navController)
                        1 -> PossitionCard("Defenders", defenders,navController)
                        2 -> PossitionCard("Midfielders", midfielders,navController)
                        3 -> PossitionCard("Attackers", attackers,navController)

                    }
                }
            }
        }

    }
}
    @Composable
    fun playerItem(playerItem: PlayerItem, navController: NavHostController) {
        Column (modifier = Modifier.clickable {
            viewmodel!!.playerDetail(playerItem)
            viewmodel!!.player(playerItem)
            navController.navigate("playerdetail")
        }){
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(6.dp)
            ) {
                AsyncImage(
                    model = playerItem.Image,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .alignByBaseline()
                        .padding(2.dp, 10.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = playerItem.Name, modifier = Modifier
                        .alignByBaseline()
                        .padding(16.dp)
                        .fillMaxWidth(0.2f)
                )
                Text(
                    text = playerItem.MarketValue, modifier = Modifier
                        .alignByBaseline()
                        .padding(16.dp)
                )
                Text(
                    text = playerItem.Number, modifier = Modifier
                        .alignByBaseline()
                        .padding(16.dp)
                )

            }
        }
    }

    @Composable
    fun PossitionCard(title: String, players: List<PlayerItem>, navController: NavHostController) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colors.surface,),
            modifier = Modifier.padding(10.dp),
        ) {
            Column{
                Text(text = title, modifier = Modifier.padding(10.dp), fontSize = 20.sp)
                for (player in players)
                    playerItem(player,navController)
            }
        }
    }

