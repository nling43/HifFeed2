package com.example.hiffeed.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.hiffeed.WebScrape.HIF
import com.example.hiffeed.WebScrape.hd
import com.example.hiffeed.WebScrape.skanesport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsRepository(application: Application) {

    var isRefreshing = MutableStateFlow(false)
    val news: LiveData<List<NewsItem>>



    private var newsDao: NewsItemDao?
    init {
        val db: AppDatabase? =  AppDatabase.getDatabase(application)
        newsDao = db?.dao()
        news = newsDao?.getAll()!!

    }
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun insert(news: NewsItem) {
        coroutineScope.launch(Dispatchers.IO) {
            asyncInsert(news)
        }
    }

    private suspend fun asyncInsert(news: NewsItem) {
        newsDao?.insert(news)
    }

    fun update() {
        clearDb()
        getNewsSkaneSport()
        getNewsHD()
        getNewsHIF()
        stopRefreshing()

    }

    private fun getNewsHD() {
        coroutineScope.launch(Dispatchers.IO) {
            val news = hd().getNews()
            for (new in news){
                insert(new)
            }

        }
    }

    private fun stopRefreshing() {
        coroutineScope.launch(Dispatchers.IO) {
            while ((news.value?.size ?: 0) < 20) {
                delay(10)
            }
            isRefreshing.update { false };

        }
    }

    private fun getNewsHIF() {
        coroutineScope.launch(Dispatchers.IO) {
            val news = HIF().getNews()
            for (new in news){
                insert(new)
            }

        }
    }

    private fun getNewsSkaneSport() {
        coroutineScope.launch(Dispatchers.IO) {
            val news = skanesport().getNews()
            for (new in news){
                insert(new)
            }
        }
    }

    private fun clearDb() {
        coroutineScope.launch(Dispatchers.IO) {
            newsDao?.clearDb()
        }


    }
}


