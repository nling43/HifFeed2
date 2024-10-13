package com.example.hiffeed.GraphQl

import android.util.Log
import com.example.hiffeed.database.MessageAndNews.Message.MessageItem
import com.example.hiffeed.database.MessageAndNews.News.NewsItem
import org.json.JSONObject

class Parse {
    fun message(jsonObject: JSONObject): MessageItem {
        var text = jsonObject.getString("TextBody").toString()
        var quotes = ""

        if (text.contains("[/QUOTE]")){
            quotes = text.substringBeforeLast("[/QUOTE]")
            text = text.substringAfterLast("[/QUOTE]").trim()
        }
        return MessageItem(jsonObject.getString("NicName").toString(), text,convertDate(jsonObject.getString("Created").toString()),extractQuotes(quotes),jsonObject.getString("Link") )
    }


    fun news(jsonObject: JSONObject): NewsItem {
            val header = jsonObject.getString("Header")
            val text = jsonObject.getString("Intro")
            val date = jsonObject.getString("Published")
            val link = "https://www.svenskafans.com/fotboll/hif/" +jsonObject.getString("UniqueURI")
            val src = "SvenskaFans"
            return NewsItem(header, text,convertDate(date), src,"", link )
        }


    private fun convertDate(dateString:String) : String {
        return  dateString.replace('T',' ').substringBeforeLast(':')

    }

    private fun extractQuotes(text:String) : List<Pair<String,String>>{

        val authors = ArrayList<String>()
        val quotes = mutableListOf<Pair<String,String>>()
        val array =  text.split("[/QUOTE]")
        try {
            if (text.isNotEmpty()) {
                var firstQuote = array[0]
                while (firstQuote.contains("[QUOTE=")) {
                    val equalsIndex = firstQuote.indexOfFirst { c -> c == '=' }
                    val endBracketIndex = firstQuote.indexOfFirst { c -> c == ']' }
                    authors.add(firstQuote.substring(equalsIndex + 1, endBracketIndex ))
                    firstQuote = firstQuote.removeRange(0, endBracketIndex + 1)
                }
                authors.reverse()
                quotes.add(Pair(authors[0], firstQuote))
                for (i in 1 until array.size) {
                    quotes.add(Pair(authors[i], array[i].trim()))
                }
            }
        }

        catch (e: Exception) {
            Log.e("Error in extractQuotes", e.message.toString())
        }

        return quotes
    }





}