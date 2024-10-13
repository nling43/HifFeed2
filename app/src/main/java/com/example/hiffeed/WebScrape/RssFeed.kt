package com.example.hiffeed.WebScrape

import android.text.Html
import android.util.Log
import com.example.hiffeed.database.MessageAndNews.News.NewsItem
import com.jcabi.xml.XMLDocument
import fuel.Fuel
import fuel.get
import java.text.SimpleDateFormat
import java.util.Locale

class RssFeed {
    private val sorces = mapOf(
        "HIF" to "https://www.HIF.se/feed/",
        "skanesport" to "https://skanesport.se/sporter/fotboll/helsingborgs-if/feed/",
        "fotbolltransfers" to "https://fotbolltransfers.com/rss/klubbar/29"
    );
    suspend fun getNews(): MutableList<NewsItem> {
        val list: MutableList<NewsItem> = mutableListOf()
        for (source in sorces) {
            try {
                val xml = XMLDocument(Fuel.get(source.value).body)

                val titles = xml.nodes("//item/title")
                val links = xml.nodes("//item/link")
                val pubDates = xml.nodes("//item/pubDate")
                val descriptions = xml.nodes("//item/description")

                for (i in 0 until 10) {
                    var header = titles[i].toString().replace("<\\/?(title)>".toRegex(), "").trim()
                    if(header.contains("<![CDATA[")){
                        header =extractHeader(titles[i].toString())

                    }
                    val link = links[i].toString().replace("<\\/?(link)>".toRegex(), "")
                    val date =
                        convertDate(pubDates[i].toString().replace("<\\/?(pubDate)>".toRegex(), ""))
                    val text = extractDescription(descriptions[i].toString())

                    val newsItem = NewsItem(header, text, date, source.key, "img", link)
                    list.add(newsItem)
                }
            } catch (e: Exception) {
                Log.e("RssFeed failure " + source.key, e.toString())
                continue
            }

        }
        return list

    }

    private fun extractDescription(content: String): String {
        val regex = "<description>\\s*<!\\[CDATA\\[\\s*(.*?)\\s*\\]\\]>\\s*</description>".toRegex(RegexOption.DOT_MATCHES_ALL)
        val regex2 = Regex("\\n+")
        val matchResult = regex.find(content)
        val description = matchResult?.groupValues?.get(1) ?: ""
        val unescapedDescription = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY).toString()
        val result = unescapedDescription.replace(regex2, "")
        val firstCharacter = result.firstOrNull()?.uppercaseChar()
        val isNotSwedishChar = firstCharacter != null && firstCharacter !in 'B'..'Z'

        val finalResult = if (isNotSwedishChar) result.drop(1) else result

        return finalResult.split("The post")[0]
    }
    private fun extractHeader(content: String): String {
        val regex = "<title>\\s*<!\\[CDATA\\[\\s*(.*?)\\s*\\]\\]>\\s*</title>".toRegex(RegexOption.DOT_MATCHES_ALL)
        val matchResult = regex.find(content)
        val header = matchResult?.groupValues?.get(1) ?: ""
        return header
    }
    private fun convertDate(dateString:String) : String {
        val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }
}