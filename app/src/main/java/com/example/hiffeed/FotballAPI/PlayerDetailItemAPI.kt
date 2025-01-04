package com.example.hiffeed.FotballAPI

data class PlayerDetailItemAPI(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<PlayerDetailResponse>,
    val results: Int
)


data class PlayerDetail(

    val id: Int,
    val name :String,
    val firstname: String,
    val lastname :String,
    val age : Int,
    val birth : Birth,
    val number: Int,
    val nationality:String,
    val height: String,
    val weight: String,
    val injured: Boolean,
    val photo: String
)

data class Birth (
    val date: String,
    val place: String,
    val country: String
)


data class  PlayerDetailResponse(
    val player: PlayerDetail,
    val statistics: List<Statistics>
)


data class  Statistics(
    val team: Team,
    val league: League,
    val games :Games,
    val substitutes: Substitutes,
    val shots: Shots,
    val goals : Goals,
    val passes :Passes,
    val tackles: Tackles,
    val duels : Duels,
    val dribbles: Dribbles,
    val fouls: Fouls,
    val cards: Cards,
    val penalty : Penalty

)
data class Passes(
    val accuracy: Int,
    val key: Int,
    val total: Int
)
data class  League(
    val id: Int,
    val name: String,
    val country: String,
    val logo: String,
    val flag: String,
    val season: Int
)
data class Tackles(
    val blocks: Int,
    val interceptions: Int,
    val total: Int
)
data class  Games(
    val appearences: Int,
    val lineups: Int,
    val minutes: Int,
    val number: Int,
    val position: String,
    val rating: Double,
    val captain : Boolean
)
data class Dribbles(
    val attempts: Int,
    val past: Any,
    val success: Int
)
data class Fouls(
    val committed: Int,
    val drawn: Int
)
data class Duels(
    val total: Int,
    val won: Int
)
data class Cards(
    val red: Int,
    val yellow: Int,
    val yellowred: Int
)
data class  Substitutes(
    val `in`: Int,
    val `out`: Int,
    val bench: Int,

)
data class Penalty(
    val commited: Any,
    val missed: Int,
    val saved: Any,
    val scored: Int,
    val won: Any
)
data class Goals(
    val assists: Int,
    val conceded: Int,
    val saves: Any,
    val total: Int
)
data class Shots(
    val on: Int,
    val total: Int
)

