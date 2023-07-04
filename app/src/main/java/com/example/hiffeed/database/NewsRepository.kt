package com.example .hiffeed.database

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.hiffeed.WebScrape.RssFeed
import com.example.hiffeed.WebScrape.hd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsRepository(application: Application) {

    var isRefreshing = MutableStateFlow(false)
    val news: LiveData<List<NewsItem>>
    val latest : LiveData<NewsItem>
    val oldest : LiveData<NewsItem>


    private var newsDao: NewsItemDao?
    init {
        val db: AppDatabase? =  AppDatabase.getDatabase(application)
        newsDao = db?.dao()
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
                hdJob.await()
                hifJob.await()
                isRefreshing.update { false };

            }
        }
    }



    private fun clearDb() {
        coroutineScope.launch(Dispatchers.IO) {
            newsDao?.clearDb()
        }


    }
}


