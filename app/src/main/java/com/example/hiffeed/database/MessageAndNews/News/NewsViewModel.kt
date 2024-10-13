package com.example.hiffeed.database.MessageAndNews.News


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.hiffeed.database.Stats.GameItemRepository
import kotlinx.coroutines.flow.StateFlow

class NewsViewModel (application: Application) :
    AndroidViewModel(application) {

    private val repository: NewsRepository = NewsRepository(application)

    val news: LiveData<List<NewsItem>> = repository.news
    val isRefreshing: StateFlow<Boolean> get() = repository.isRefreshing

    fun update(){
        repository.update();
    }

    fun refresh() {
        update();
    }


}