package com.example.hiffeed.database.MessageAndNews.Message

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.example.hiffeed.GraphQl.Parse
import com.example.hiffeed.GraphQl.Requests
import com.example.hiffeed.GraphQl.VolleyClient
import com.example.hiffeed.database.MessageAndNews.MessageAndNewsDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONArray

class MessageRepository(application: Application) {
    val url = "https://graphql.svenskafans.com/graphql"

    var isRefreshing = MutableStateFlow(false)
    val messages: LiveData<List<MessageItem>>

    val volleyClient: VolleyClient? =  VolleyClient.getInstance(application)
    private val context : Context?

    private var messageDao: MessageItemDao?

    init {
        context = application
        val db: MessageAndNewsDatabase? = MessageAndNewsDatabase.getDatabase(application)

        messageDao = db?.messageDao()
        messages = messageDao?.getAll()!!



    }
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun insertMessage(message: MessageItem) {
        coroutineScope.launch(Dispatchers.IO) {
            messageDao?.insert(message)
        }
    }

    private fun deleteMessage(message: MessageItem) {
        coroutineScope.launch(Dispatchers.IO) {
            messageDao?.delete(message)
        }
    }


    fun update() {
        isRefreshing.update { true };
        clearDb()
        requestData()
        isRefreshing.update { false }

    }


    private fun requestData() {
        coroutineScope.launch(Dispatchers.IO) {
            volleyClient?.addToRequestQueue(buildMessageRequest())
        }
    }



    private fun buildMessageRequest(): JsonArrayRequest {
        val jsonObjectRequest = JsonArrayRequest(Request.Method.POST, url, JSONArray().put(Requests().getMessagesRequest()),
            { response ->
                val jsonArray= response.getJSONObject(0).getJSONObject("data").getJSONArray("teamForumMessages")
                for (i in 0 until jsonArray.length()){
                    insertMessage(Parse().message(jsonArray.getJSONObject(i)))

                }
            },
            { error ->
                Log.d("getMessages", error.networkResponse.toString())
                isRefreshing.update { false };
            }
        )
        return jsonObjectRequest
    }




    private fun clearDb() {
        coroutineScope.launch(Dispatchers.IO) {
            messageDao?.clearDb()
        }


    }
}


