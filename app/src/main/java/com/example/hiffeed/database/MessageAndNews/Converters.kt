package com.example.hiffeed.database.MessageAndNews

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromList(list: List<Pair<String, String>>): String {
        var stringToDb = ""
        for (pair in list) {
            stringToDb += "({[${pair.first} ::: ${pair.second}]})"
        }
        return stringToDb
    }
    @TypeConverter
    fun toList(string: String): List<Pair<String, String>> {
        val quotes = mutableListOf<Pair<String, String>>()
        var string = string
        if (string.isNotEmpty()) {
            while (string.contains("]})")) {
                val subString = string.substringAfter("({[").substringBefore("]})")
                quotes.add(Pair(subString.substringBefore(":::"), subString.substringAfter(":::")))
                string = string.substringAfter("]})")
            }
        }
        return quotes

    }
}