package com.programmazionemobile.formula1app.data.constructorStandingsData


import com.google.gson.annotations.SerializedName

data class StandingsLists(
    @SerializedName("ConstructorStandings")
    val constructorStandings: List<ConstructorStanding>,
    @SerializedName("round")
    val round: String, // 13
    @SerializedName("season")
    val season: String // 2023
)