package com.programmazionemobile.formula1app.data.sprintData


import com.google.gson.annotations.SerializedName

data class RaceTable(
    @SerializedName("Races")
    val races: List<Race>,
    @SerializedName("round")
    val round: String, // 17
    @SerializedName("season")
    val season: String // 2023
)