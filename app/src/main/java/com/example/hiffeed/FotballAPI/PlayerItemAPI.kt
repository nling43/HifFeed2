package com.example.hiffeed.FotballAPI

data class PlayerItemAPI(
    val errors: List<Any>,
    val `get`: String,
    val paging: Paging,
    val parameters: Parameters,
    val response: List<Response>,
    val results: Int
)
data class Paging(
    val current: Int,
    val total: Int
)

data class Parameters(
    val team: String
)

data class Player(
    val age: Int,
    val id: Int,
    val name: String,
    val number: Int,
    val photo: String,
    val position: String
)



data class Response(
    val players: List<Player>,
    val team: Team
)

data class Team(
    val id: Int,
    val logo: String,
    val name: String
)

