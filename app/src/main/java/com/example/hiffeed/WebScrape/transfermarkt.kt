package com.example.hiffeed.WebScrape

import android.util.Log
import fuel.get
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.util.Calendar

class transfermarkt {

    private val season = Calendar.getInstance().get(Calendar.YEAR) -1
    private val url =
        "https://www.transfermarkt.com/helsingborgs-if/kader/verein/699/saison_id/$season/plus/1";

    fun getPlayers(): MutableList<PlayerItemTransferWebScrape> {
        val list :MutableList<PlayerItemTransferWebScrape> = mutableListOf();
        try {
            val doc: Document = Jsoup.connect(url).get()
            val rows = doc.select("tbody tr")
            for (row in rows) {
                val name = row.select("td.hauptlink a").text()
                val contractEndDate = ""
                val marketValue  = row.select("td.rechts.hauptlink a").text()
                list.add(PlayerItemTransferWebScrape(name,contractEndDate, marketValue));
            }
        } catch (e: Exception) {
            Log.e("WebScrape", e.toString())
            return list
        }
        return list
    }
}
data class PlayerItemTransferWebScrape(
    val name: String,
    val contractEndDate : String,
    val marketValue : String,
)