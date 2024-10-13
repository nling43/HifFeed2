package com.example.hiffeed.database.Stats

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hiffeed.FotballAPI.Player
import com.example.hiffeed.FotballAPI.PlayerDetailItemAPI
import com.example.hiffeed.FotballAPI.PlayerItemAPI
import com.example.hiffeed.FotballAPI.VolleyClient
import com.example.hiffeed.WebScrape.PlayerItemWebScrape
import com.example.hiffeed.WebScrape.hif
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PlayerItemRepository(application: Application) {

    var isRefreshing = MutableStateFlow(false)
    val players: LiveData<List<PlayerItem>>
    val defenders: LiveData<List<PlayerItem>>
    val goalkeepers: LiveData<List<PlayerItem>>
    val attackers: LiveData<List<PlayerItem>>
    val midfielders: LiveData<List<PlayerItem>>
    var playerDetail = MutableLiveData<PlayerDetailItemAPI>()




    private val context : Context?
    private var playersDao: PlayerItemDao?
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        context = application
        val db: StatsDatabase? = StatsDatabase.getDatabase(application)
        playersDao = db?.playerDao()
        players = playersDao?.getAll()!!
        defenders= playersDao?.getDefenders()!!
        goalkeepers= playersDao?.getGoalkeepers()!!
        attackers= playersDao?.getAttackers()!!
        midfielders= playersDao?.getMidfielders()!!
    }


    fun getPlayers() {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val playerApi= ArrayList<Player>()
                val players =  mutableListOf<PlayerItemWebScrape>()
                val hifJob = async {
                    players.addAll(hif().getPlayers())
                }


                if (context != null) {
                    VolleyClient(context).getPlayers()
                        .subscribeOn(Schedulers.newThread())
                        .subscribeWith(object : DisposableSingleObserver<PlayerItemAPI>() {
                            override fun onSuccess(t: PlayerItemAPI) {
                                for (player in t.response[0].players) {

                                    playerApi.add(player)
                                }
                            }

                            override fun onError(e: Throwable) {
                                Log.e("api", e.stackTraceToString())
                            }


                        })
                }

                hifJob.await()
                for (player in players){
                    var findplayer = playerApi.find { x ->
                        x.name == player.name || x.name == convertToApiName(player.name)
                    }
                    var image = player.image
                    if (player.image == "https://www.hif.se/wp-content/uploads/2021/03/personbild-center-top-300x300.png" && findplayer != null)
                        image = findplayer.photo
                    if (findplayer != null) {
                        insert(PlayerItem(image, player.number, findplayer.id, player.position,player.name ))
                    }
                    else insert(PlayerItem(image, player.number, -1 , player.position,player.name ))


                }
                isRefreshing.update { false };


            }
        }
    }

    fun convertToApiName (name :String) :String{
        val firstLetter = name[0]
        val lasName = name.substringAfter(' ')
        return "$firstLetter. $lasName"
    }
    fun insert(player: PlayerItem) {
        coroutineScope.launch(Dispatchers.IO) {
            playersDao?.insert(player)
        }
    }

    private fun delete(player: PlayerItem) {
        coroutineScope.launch(Dispatchers.IO) {
            playersDao?.delete(player)
        }
    }


    fun update() {
        isRefreshing.update {
            true
        };
        clearDb()
        getPlayers()

    }



    private fun clearDb() {
        coroutineScope.launch(Dispatchers.IO) {
            playersDao?.clearDb()
        }
    }

    fun playersDetail(playerItem: PlayerItem, season : Int) {
        isRefreshing.update{
            true
        }
        if (context != null) {
            VolleyClient(context).getPlayerDetail(playerItem.ID, season)
                .subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableSingleObserver<PlayerDetailItemAPI>() {
                    override fun onSuccess(t: PlayerDetailItemAPI) {
                        playerDetail.postValue(t)
                        isRefreshing.update{
                            false
                        }
                    }

                    override fun onError(e: Throwable) {
                        Log.e("api", e.stackTraceToString())
                    }


                })
        }
    }

    fun getPlayer(id: Int): LiveData<PlayerItem>? {
        return playersDao?.getPlayer(id)
    }
}