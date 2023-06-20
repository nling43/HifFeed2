package com.example.hiffeed.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao

interface NewsItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: NewsItem)

    @Delete
    fun delete(news: NewsItem)

    @Query("SELECT * FROM newsTable ORDER BY date DESC")
    fun getAll(): LiveData<List<NewsItem>>

    @Query("DELETE FROM newsTable")
    suspend fun clearDb()


}
