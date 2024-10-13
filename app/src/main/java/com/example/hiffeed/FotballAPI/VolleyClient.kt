
package com.example.hiffeed.FotballAPI

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.SingleEmitter

class VolleyClient(context: Context) {

    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private val gson = Gson()
    private val teamId = 811
    private val apiUrl = "https://v3.football.api-sports.io/"

    fun getPlayers(): Single<PlayerItemAPI> {
        return Single.create { emitter: SingleEmitter<PlayerItemAPI> ->
            val url = "$apiUrl/players?team=$teamId"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    try {
                        val playerItemAPI = gson.fromJson(response.toString(), PlayerItemAPI::class.java)
                        emitter.onSuccess(playerItemAPI)
                    } catch (e: Exception) {
                        emitter.onError(e)
                    }
                },
                Response.ErrorListener { error ->
                    emitter.onError(error)
                }
            )
            requestQueue.add(jsonObjectRequest)
        }
    }

    fun getPlayerDetail(playerID: Int, season: Int): Single<PlayerDetailItemAPI> {
        return Single.create { emitter: SingleEmitter<PlayerDetailItemAPI> ->
            val url = "$apiUrl/players?id=$playerID&season=$season"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    try {
                        val playerDetailItemAPI = gson.fromJson(response.toString(), PlayerDetailItemAPI::class.java)
                        emitter.onSuccess(playerDetailItemAPI)
                    } catch (e: Exception) {
                        emitter.onError(e)
                    }
                },
                Response.ErrorListener { error ->
                    emitter.onError(error)
                }
            )
            requestQueue.add(jsonObjectRequest)
        }
    }
}

