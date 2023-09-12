package com.programmazionemobile.formula1app.data.raceResultsData


import com.google.gson.annotations.SerializedName

data class Race(
    @SerializedName("Circuit")
    val circuit: Circuit,
    @SerializedName("date")
    val date: String, // 2023-03-05
    @SerializedName("raceName")
    val raceName: String, // Bahrain Grand Prix
    @SerializedName("Results")
    val results: List<Result>,
    @SerializedName("round")
    val round: String, // 1
    @SerializedName("season")
    val season: String, // 2023
    @SerializedName("time")
    val time: String, // 15:00:00Z
    @SerializedName("url")
    val url: String // https://en.wikipedia.org/wiki/2023_Bahrain_Grand_Prix
)