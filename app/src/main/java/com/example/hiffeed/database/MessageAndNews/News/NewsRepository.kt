package com.example.hiffeed.database.MessageAndNews.News

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.hiffeed.GraphQl.Parse
import com.example.hiffeed.GraphQl.Requests

import com.example.hiffeed.WebScrape.RssFeed
import com.example.hiffeed.WebScrape.hd
import com.example.hiffeed.GraphQl.VolleyClient
import com.example.hiffeed.database.MessageAndNews.MessageAndNewsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray


class NewsRepository(application: Application) {

    var isRefreshing = MutableStateFlow(false)
    val news: LiveData<List<NewsItem>>
    val latest : LiveData<NewsItem>
    val oldest : LiveData<NewsItem>
    val volleyClient: VolleyClient?

    private var newsDao: NewsItemDao?
    init {
        val db: MessageAndNewsDatabase? = MessageAndNewsDatabase.getDatabase(application)
        volleyClient =  VolleyClient.getInstance(application)
        newsDao = db?.newsDao()
        news = newsDao?.getAll()!!
        latest = newsDao?.getFirst()!!
        oldest = newsDao?.getLast()!!

    }
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    fun insert(news: NewsItem) {
        coroutineScope.launch(Dispatchers.IO) {
            newsDao?.insert(news)
        }
    }
    private fun delete(news: NewsItem) {
        coroutineScope.launch(Dispatchers.IO) {
            newsDao?.delete(news)
        }
    }
    fun update() {
        isRefreshing.update { true };
        clearDb()
        fromEmpty()
    }
    private fun fromEmpty(){
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                val hdJob = async {
                    val news = hd().getNews()
                    for (new in news){
                        insert(new)
                    } }
                val hifJob = async {
                    val news = RssFeed().getNews()
                    for (new in news){
                        insert(new)


                    } }
                volleyClient?.addToRequestQueue(buildEzineNewsRequest())
                hdJob.await()
                hifJob.await()
                isRefreshing.update { false };

            }
        }
    }

    private fun buildEzineNewsRequest(): JsonArrayRequest {

        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.POST, "https://graphql.svenskafans.com/graphql", JSONArray().put(Requests().getNewsRequest()),
            { response ->
                val jsonArray= response.getJSONObject(0).getJSONObject("data").getJSONArray("teamArticlesFull")
                for (i in 0 until 5){
                    insert(Parse().news((jsonArray.getJSONObject(i))))
                }
            },
            { error ->
                Log.d("getNews", error.networkResponse.statusCode.toString())
                isRefreshing.update { false };


            }
        )
        return jsonObjectRequest
    }

    private fun clearDb() {
        coroutineScope.launch(Dispatchers.IO) {
            newsDao?.clearDb()
        }
    }
}


