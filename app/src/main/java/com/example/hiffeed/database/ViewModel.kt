package com.example.hiffeed.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.StateFlow

class ViewModel (application: Application) :
    AndroidViewModel(application) {

    private val repository: NewsRepository =
        NewsRepository(application)
     val news: LiveData<List<NewsItem>> = repository.news
    val isRefreshing: StateFlow<Boolean>
        get() = repository.isRefreshing

    fun insert(answer: NewsItem) {
        repository.insert(answer)
    }



    fun update(){
        repository.update();
    }

    fun refresh() {
        repository.update();
    }


}