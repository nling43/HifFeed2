package com.example.hiffeed.GraphQl

import android.util.Log
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.TemporalAdjusters
import java.util.Date


class Requests {
    val TeamID = 82

    fun getMessagesRequest(): JSONObject{
        val variables= JSONObject()
        val json = JSONObject()

        variables.put("TeamID",TeamID)
        variables.put("GameID",0)
        variables.put("PageNo",1)

        json.put("operationName", null)
        json.put("query", "query (\$TeamID: Int!, \$GameID: Int!, \$PageNo: Int!) {\n  " +
                "teamForumMessages(TeamID: \$TeamID, GameID: \$GameID, PageNo: \$PageNo) {\n  TextBody\n   " +
                " NicName\n  Created\n Link\n" +
        "   }\n}\n")
        json.put("variables", variables)

        return json
}


    fun getNewsRequest(): JSONObject{
        val json = JSONObject()
        val variables= JSONObject()
        variables.put("Limit",5)
        variables.put("offset",0)
        variables.put("TeamID",TeamID)
        json.put("operationName", "teamArticlesFull")
        json.put("query", "query teamArticlesFull(\$TeamID: Int!, \$limit: Int, \$offset: Int) {\n" +
                    "  teamArticlesFull(TeamID: \$TeamID, limit: \$limit, offset: \$offset) {\n" +
                    "    Header\n" +
                    "    Intro\n" +
                    "    Published\n" +
                    "    UniqueURI\n" +
                    "    Published\n" +
                    "  }\n" +
                    "}")
        json.put("variables", variables)
        return json
    }


}