package com.example.hiffeed.WebScrape

import android.util.Log
import com.example.hiffeed.database.NewsItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.time.LocalDate

class hd {
    private val url ="https://www.hd.se/story/9ef9ff08-82a5-421c-aa1f-e8db7a2e65d4";

    fun getNews(): MutableList<NewsItem> {
        val list :MutableList<NewsItem> = mutableListOf();
        try {
            val document: Document = Jsoup.connect(url).get()
            val articleElements: List<Element> = document.select("article")

                for (i in 0 until 10 ) {
                    val articleElement = articleElements[i]

                    val heading = articleElement.getElementsByClass("prose-title").text().toString()
                    val text = articleElement.getElementsByClass("txt-body").text().toString()
                    val link = "https://www.hd.se"+articleElement.select("a").attr("href").toString()
                    val img = articleElement.select("img").attr("data-src").toString()
                    var date = articleElement.select("a").attr("href").substring(1,11)


                        val webpage: Document = Jsoup.connect(link).get()
                        date += " " + webpage.select("time").text().toString()
                            .substring(webpage.select("time").text().toString().length - 5);



                    list.add(NewsItem(heading,text,date,"hd.se",img, link))

                }

        } catch (e: Exception) {
            Log.e("webscrape failure hd", e.toString())
            return list


        }
        return list

    }


}