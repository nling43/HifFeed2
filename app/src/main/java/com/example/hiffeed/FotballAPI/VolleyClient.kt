
package com.example.hiffeed.FotballAPI

import android.content.Context
import android.util.Log
import com.android.volley.Cache
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.NoCache
import com.android.volley.toolbox.Volley
import com.example.hiffeed.BuildConfig
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.SingleEmitter
import org.json.JSONObject
import java.io.File

class VolleyClient(context: Context) {
    // Instantiate the cache
    val cache = DiskBasedCache(context.cacheDir, 10 * 1024 * 1024)
    val network = BasicNetwork(HurlStack())
    val requestQueue = RequestQueue(cache, network).apply {
        start()
    }
    private val gson = Gson()
    private val teamId = 811
    private val apiUrl = "https://v3.football.api-sports.io/"
    private val apiKey = BuildConfig.API_KEY



    fun getPlayers(): Single<PlayerItemAPI> {
        val url = "$apiUrl/players/squads?team=$teamId"

        return Single.create { emitter ->
            val cachedResponse = cache.get(url)

            if (cachedResponse != null) {
                val playerItemAPI = gson.fromJson(String(cachedResponse.data), PlayerItemAPI::class.java)
                emitter.onSuccess(playerItemAPI)
            } else {
                val jsonObjectRequest = object : JsonObjectRequest(
                    Method.GET, url, null,
                    { response ->
                        val playerItemAPI = gson.fromJson(response.toString(), PlayerItemAPI::class.java)
                        emitter.onSuccess(playerItemAPI)
                        putInCache(response, url)
                    },
                    { error ->
                        emitter.onError(error)
                    }
                ) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["X-RapidAPI-Key"] = apiKey
                        headers["Content-Type"] = "application/json"
                        headers["Cache-Control"] = "max-age=3600"

                        return headers
                    }


                }

                requestQueue.add(jsonObjectRequest)
            }
        }
    }

    fun getPlayerDetail(playerId: Int, season: Int): Single<PlayerDetailItemAPI> {
        val url = "$apiUrl/players?id=$playerId&season=$season"

        return Single.create { emitter ->
            val cachedResponse = cache.get(url)
            if (cachedResponse != null) {
                val playerDetailItemAPI =
                    gson.fromJson(String(cachedResponse.data), PlayerDetailItemAPI::class.java)
                emitter.onSuccess(playerDetailItemAPI)
            } else {
                val jsonObjectRequest = object : JsonObjectRequest(
                    Method.GET, url, null,
                    { response ->

                        val playerDetailItemAPI =
                            gson.fromJson(response.toString(), PlayerDetailItemAPI::class.java)
                        emitter.onSuccess(playerDetailItemAPI)
                        putInCache(response, url)

                    },
                    { error ->
                        emitter.onError(error)
                    }
                ) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["X-RapidAPI-Key"] = apiKey
                        headers["Content-Type"] = "application/json"
                        headers["Cache-Control"] = "max-age=3600"

                        return headers
                    }
                }
                requestQueue.add(jsonObjectRequest)
            }
        }
    }

    fun putInCache(response: JSONObject, cacheKey : String){
        val entry = Cache.Entry()
        entry.data= response.toString().toByteArray()
        cache.put(cacheKey, entry)
    }

}
