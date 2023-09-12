package com.programmazionemobile.formula1app.data.raceResultsData


import com.google.gson.annotations.SerializedName

data class RaceTable(
    @SerializedName("Races")
    val races: List<Race>,
    @SerializedName("round")
    val round: String, // 1
    @SerializedName("season")
    val season: String // 2023
)