package com.example.hiffeed.database.MessageAndNews.Message

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messageTable")

class MessageItem(
    @ColumnInfo(name = "NicName") var NicName: String,
    @ColumnInfo(name = "TextBody") var TextBody: String,
    @ColumnInfo(name = "Date") var Date: String,
    @ColumnInfo(name = "Quotes") var Quotes: List<Pair<String,String>>,
    @ColumnInfo(name = "Link") var Link: String,


    ) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}