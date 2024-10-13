package com.example.hiffeed.database.Stats

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "gameTable")

class GameItem (
    @ColumnInfo(name = "isHomeTeam") var isHomeTeam: Boolean,
    @ColumnInfo(name = "EvilTeam") var EvilTeam: String,
    @ColumnInfo(name = "Competition") var Competition: String,
    @ColumnInfo(name = "Status") var Status: String,
    @ColumnInfo(name = "Date") var Date: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") var id: String
)