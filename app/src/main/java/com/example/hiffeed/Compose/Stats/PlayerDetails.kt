package com.example.hiffeed.Compose.Stats

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.example.hiffeed.Compose.LoadingItem
import com.example.hiffeed.FotballAPI.PlayerDetailItemAPI
import com.example.hiffeed.database.Stats.PlayerItem
import com.example.hiffeed.database.Stats.PlayerViewModel


@Composable
fun PlayerDetails(playersViewmodel : PlayerViewModel) {
    val playerDetail = playersViewmodel.playerDetail.observeAsState()

    val isRefreshing by playersViewmodel.isRefreshing.collectAsState()
    if (playerDetail?.value == null ) {
            LoadingItem()
        } else {
        val player = playersViewmodel.player(playerDetail.value!!.response[0].player.id)

        detail(playerDetail)
        }
    }

@Composable
fun detail(playerDetail: State<PlayerDetailItemAPI?>) {
    Column {
        Text(text = playerDetail?.value?.response.toString())


    }
}


