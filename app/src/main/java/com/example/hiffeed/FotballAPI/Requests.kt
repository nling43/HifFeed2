package com.example.hiffeed.FotballAPI

import com.example.hiffeed.BuildConfig
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val apiKey = BuildConfig.API_KEY

interface Requests {

    @Headers("X-RapidAPI-Key: $apiKey")
    @GET("/players/squads")
    fun getPlayers(@Query("team") team: Int): Single<PlayerItemAPI>

    @Headers("X-RapidAPI-Key: $apiKey")
    @GET("/players")
    fun getPlayerDetail(
            @Query("id") playerID: Int,
            @Query("season") season: String
    ): Single<PlayerDetailItemAPI>
}
