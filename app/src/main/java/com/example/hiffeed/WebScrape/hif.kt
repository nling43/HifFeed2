package com.example.hiffeed.WebScrape

import android.util.Log
import androidx.compose.material3.TabPosition
import com.example.hiffeed.FotballAPI.Paging
import com.example.hiffeed.FotballAPI.Parameters
import com.example.hiffeed.FotballAPI.Response
import com.example.hiffeed.database.MessageAndNews.News.NewsItem
import com.example.hiffeed.database.Stats.PlayerItem
import com.jcabi.xml.XMLDocument
import fuel.Fuel
import fuel.get
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class hif {


    private val url ="https://www.hif.se/herrar/";

    fun getPlayers(): MutableList<PlayerItemWebScrape> {
        val list :MutableList<PlayerItemWebScrape> = mutableListOf();
        try {
            val document: Document = Jsoup.connect(url).get()
            val playerElements: List<Element> = document.select(".staff-entry")

            for (playerElement in playerElements ) {

                val image = playerElement.getElementsByClass("staff-entry-media-img").attr("src").toString()
                val name = playerElement.getElementsByClass("staff-entry-title").text().trim()
                val number = playerElement.getElementsByClass("staff-entry-position").text().trim()
                val position = playerElement.getElementsByClass("staff-entry-categories").text().trim()



                list.add(PlayerItemWebScrape(image,name,position,number))

            }

        } catch (e: Exception) {
            Log.e("WebScrape", e.toString())
            return list


        }
        return list

    }
}
data class PlayerItemWebScrape(
    val image: String,
    val name: String,
    val position: String,
    val number: String,

)