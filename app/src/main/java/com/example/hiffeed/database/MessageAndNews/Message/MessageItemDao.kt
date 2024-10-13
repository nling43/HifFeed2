package com.example.hiffeed.database.MessageAndNews.Message

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao

interface MessageItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(message: MessageItem)

    @Delete
    fun delete(message: MessageItem)

    @Query("SELECT * FROM messageTable  ORDER BY date DESC")
    fun getAll(): LiveData<List<MessageItem>>


    @Query("DELETE FROM messageTable")
    fun clearDb()



}
