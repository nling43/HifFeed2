package com.example.hiffeed.WebScrape

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.hiffeed.database.NewsItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class skanesport {
    private val url ="https://skanesport.se/sporter/fotboll/helsingborgs-if/";

    fun getNews(): MutableList<NewsItem> {
        val list :MutableList<NewsItem> = mutableListOf();
        try {
                val document: Document = Jsoup.connect(url).get()

                val articleElements: List<Element> = document.select("article")
            Log.e("skanesport",articleElements.toString())

            for (i in 0 until 10 ) {
                val articleElement = articleElements[i]
                    val heading = articleElement.getElementsByClass("entry-title").text().toString()
                    val  link = articleElement.getElementsByClass("entry-title").select("a").attr("href").toString()
                    val text =articleElement.getElementsByClass("entry-content").select("p").text().toString()
                    val date = articleElement.getElementsByClass("entry-date").attr("datetime")
                    val img = articleElement.select("img").attr("src").toString()

                    val time = articleElement.select("span.year").text().toString().substring(articleElement.select("time").text().toString().length - 5);

                    list.add(NewsItem(heading,text, "$date $time","skånesport.se",img,link))


                }

            } catch (e: Exception) {
            Log.e("webscrape failure skanesport", e.toString())

        }
        return list

    }

}