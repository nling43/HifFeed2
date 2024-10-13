package com.example.hiffeed.database.MessageAndNews.Message


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.StateFlow

class ForumViewModel (application: Application) :
    AndroidViewModel(application) {

    private val repository: MessageRepository = MessageRepository(application)
    val messages: LiveData<List<MessageItem>> = repository.messages

    val isRefreshing: StateFlow<Boolean> get() = repository.isRefreshing

    fun update(){
        repository.update();

    }

    fun refresh() {
        update();
    }


}