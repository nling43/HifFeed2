package com.example.hiffeed.database.Stats

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teamTable")

class TeamItem(
    @ColumnInfo(name = "Position") var Position: String,
    @ColumnInfo(name = "Wins") var Wins: String,
    @ColumnInfo(name = "Lost") var Lost: String,
    @ColumnInfo(name = "Draws") var Draws: String,
    @ColumnInfo(name = "Logo") var Logo: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Name") var Name: String
    )