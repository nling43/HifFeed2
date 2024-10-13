package com.example.hiffeed.database.Stats

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.hiffeed.BuildConfig
import com.example.hiffeed.GraphQl.VolleyClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient


class GameItemRepository(application: Application) {

    var isRefreshing = MutableStateFlow(false)
    val games: LiveData<List<GameItem>>
    val apiUrl = "https://v3.football.api-sports.io/"
    val apiKey = BuildConfig.API_KEY
    val volleyClient: VolleyClient? =  VolleyClient.getInstance(application)
    private val context : Context?
    private var gamesDao: GameItemDao?
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        context = application
        val db: StatsDatabase? = StatsDatabase.getDatabase(application)

        gamesDao = db?.gamesDao()
        games = gamesDao?.getAll()!!



    }


    fun getGames(){
        buildGameRequest()

    }

    private fun buildGameRequest() {
        var client = OkHttpClient()
        


    }


    fun insert(game: GameItem) {
        coroutineScope.launch(Dispatchers.IO) {
            gamesDao?.insert(game)
        }
    }

    private fun delete(game: GameItem) {
        coroutineScope.launch(Dispatchers.IO) {
            gamesDao?.delete(game)
        }
    }


    fun update() {
        isRefreshing.update { true };
        clearDb()
        isRefreshing.update { false }

    }



    private fun clearDb() {
        coroutineScope.launch(Dispatchers.IO) {
            gamesDao?.clearDb()
        }


    }
}


