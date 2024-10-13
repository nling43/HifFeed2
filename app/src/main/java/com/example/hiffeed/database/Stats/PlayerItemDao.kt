package com.example.hiffeed.database.Stats

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlayerItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(playerItem: PlayerItem)

    @Delete
    fun delete(playerItem: PlayerItem)

    @Query("SELECT * FROM playerTable")
    fun getAll(): LiveData<List<PlayerItem>>

    @Query("SELECT * FROM playerTable where Position = 'Målvakt'")
    fun getGoalkeepers(): LiveData<List<PlayerItem>>
    @Query("SELECT * FROM playerTable where Position = 'Back'")
    fun getDefenders(): LiveData<List<PlayerItem>>

    @Query("SELECT * FROM playerTable where Position = 'Mittfältare'")
    fun getMidfielders(): LiveData<List<PlayerItem>>

    @Query("SELECT * FROM playerTable where Position = 'Forward'")
    fun getAttackers(): LiveData<List<PlayerItem>>


    @Query("SELECT * FROM playerTable where ID = :id ")
    fun getPlayer(id :Int): LiveData<PlayerItem>

    @Query("DELETE FROM playerTable")
    fun clearDb()



}
