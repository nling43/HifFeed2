package com.example.hiffeed.database.Stats


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hiffeed.FotballAPI.PlayerDetailItemAPI
import kotlinx.coroutines.flow.StateFlow

class PlayerViewModel (application: Application) :
    AndroidViewModel(application) {

    private val repository: PlayerItemRepository = PlayerItemRepository(application)

    val players: LiveData<List<PlayerItem>> = repository.players
    val goalkeepers: LiveData<List<PlayerItem>> = repository.goalkeepers
    val attackers: LiveData<List<PlayerItem>> = repository.attackers
    val midfielders: LiveData<List<PlayerItem>> = repository.midfielders
    val defenders: LiveData<List<PlayerItem>> = repository.defenders
    val playerDetail : MutableLiveData<PlayerDetailItemAPI> = repository.playerDetail
    val player : LiveData<PlayerItem> = repository.player


    val isRefreshing: StateFlow<Boolean> get() = repository.isRefreshing

    fun update(){
        repository.update();
    }

    fun refresh() {
        update();
    }

    fun playerDetail(playerItem: PlayerItem) {
        repository.playersDetail(playerItem, 2024 );

    }
    fun player(playerItem: PlayerItem) {
        return repository.getPlayer(playerItem)
    }



}