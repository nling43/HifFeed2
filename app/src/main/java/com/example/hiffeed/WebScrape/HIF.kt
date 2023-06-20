package com.example.hiffeed.WebScrape

import android.util.Log
import com.example.hiffeed.database.NewsItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class HIF {private val url ="https://www.HIF.se/nyheter/";

    fun getNews(): MutableList<NewsItem> {
        val list :MutableList<NewsItem> = mutableListOf();
        var lastDate = ""
        try {
            val document: Document = Jsoup.connect(url).get()
            val articleElements: List<Element> = document.select("article")
            for (articleElement in articleElements) {
                val heading = articleElement.getElementsByClass("blog-entry-title").text().toString()
                val  link = articleElement.getElementsByClass("blog-entry-title").select("a").attr("href").toString()

                val text = articleElement.select("p").text()
                val img = articleElement.select("img").attr("src").toString()
                var date = articleElement.getElementsByClass("updated").attr("datetime")
                if (date == ""){
                    date=lastDate
                }
                else{
                    lastDate=date
                }
                val webpage: Document = Jsoup.connect(link).get()
                val time = webpage.selectFirst("meta[property=article:published_time]")?.attr("content")
                val formatter = DateTimeFormatter.ofPattern("HH:mm")

                val formattedTime = OffsetDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME).plusHours(2).format(formatter);


                list.add(NewsItem(heading,text, "$date $formattedTime","HIF.se",img,link))

            }

        } catch (e: Exception) {
            Log.e("webscrape failure HIF", e.toString())

        }
        return list

    }

}