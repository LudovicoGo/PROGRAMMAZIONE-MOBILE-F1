package com.programmazionemobile.formula1app.data.qualifyingData


import com.google.gson.annotations.SerializedName

data class Race(
    @SerializedName("Circuit")
    val circuit: Circuit,
    @SerializedName("date")
    val date: String, // 2023-07-09
    @SerializedName("QualifyingResults")
    val qualifyingResults: List<QualifyingResult>,
    @SerializedName("raceName")
    val raceName: String, // British Grand Prix
    @SerializedName("round")
    val round: String, // 10
    @SerializedName("season")
    val season: String, // 2023
    @SerializedName("time")
    val time: String, // 14:00:00Z
    @SerializedName("url")
    val url: String // https://en.wikipedia.org/wiki/2023_British_Grand_Prix
)