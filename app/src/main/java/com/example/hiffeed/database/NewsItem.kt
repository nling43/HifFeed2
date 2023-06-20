package com.example.hiffeed.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newsTable")

class NewsItem(
    @ColumnInfo(name = "header") var header: String,
    @ColumnInfo(name = "text") var text: String,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "src") var src: String,
    @ColumnInfo(name = "img") var img: String,
    @ColumnInfo(name = "link") var link: String

) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0


}
