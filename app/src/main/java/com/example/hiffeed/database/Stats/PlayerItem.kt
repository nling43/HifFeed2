package com.example.hiffeed.database.Stats

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playerTable")

class PlayerItem (
    @ColumnInfo(name = "Image") var Image: String,
    @ColumnInfo(name = "Number") var Number: String,
    @ColumnInfo(name = "id") var ID: Int,
    @ColumnInfo(name = "Position") var Position: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Name") var Name: String,


    )