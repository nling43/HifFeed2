package com.example.hiffeed.Compose.Stats

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hiffeed.Compose.LoadingItem
import com.example.hiffeed.FotballAPI.PlayerDetailItemAPI
import com.example.hiffeed.database.Stats.PlayerItem
import com.example.hiffeed.database.Stats.PlayerViewModel
import kotlinx.coroutines.flow.StateFlow


@Composable

fun PlayerDetails(playersViewmodel: PlayerViewModel) {
    val playerDetail by playersViewmodel.playerDetail.observeAsState()
    val player by playersViewmodel.player.observeAsState()

    val isRefreshing by playersViewmodel.isRefreshing.collectAsState()

    if (isRefreshing || playerDetail == null || player == null || playerDetail!!.response.isEmpty()) {
        LoadingItem()
    } else {
        PlayerDetailUI(playerDetail = playerDetail!!, playerItem = player!!)

    }
}
@Composable
fun PlayerDetailUI(playerDetail: PlayerDetailItemAPI, playerItem: PlayerItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

    ) {
        val playerD = playerDetail.response[0].player
        Text(
            text = playerItem.Name, style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)

            ) {
            }
        }
    }
}