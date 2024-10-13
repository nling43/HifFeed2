package com.example.hiffeed.database.Stats

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GameItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gameItem: GameItem)

    @Delete
    fun delete(gameItem: GameItem)

    @Query("SELECT * FROM gameTable  ORDER BY date DESC")
    fun getAll(): LiveData<List<GameItem>>

    @Query("DELETE FROM gameTable")
    fun clearDb()



}
