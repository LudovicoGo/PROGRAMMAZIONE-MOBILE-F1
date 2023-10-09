package com.programmazionemobile.formula1app.data.qualifyingData


import com.google.gson.annotations.SerializedName

data class RaceTable(
    @SerializedName("Races")
    val races: List<Race>,
    @SerializedName("round")
    val round: String, // 10
    @SerializedName("season")
    val season: String // 2023
)