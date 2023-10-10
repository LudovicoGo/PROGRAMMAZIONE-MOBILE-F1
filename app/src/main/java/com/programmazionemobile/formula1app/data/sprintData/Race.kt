package com.programmazionemobile.formula1app.data.sprintData


import com.google.gson.annotations.SerializedName

data class Race(
    @SerializedName("Circuit")
    val circuit: Circuit,
    @SerializedName("date")
    val date: String, // 2023-10-08
    @SerializedName("raceName")
    val raceName: String, // Qatar Grand Prix
    @SerializedName("round")
    val round: String, // 17
    @SerializedName("season")
    val season: String, // 2023
    @SerializedName("SprintResults")
    val sprintResults: List<SprintResult>,
    @SerializedName("time")
    val time: String, // 17:00:00Z
    @SerializedName("url")
    val url: String // https://en.wikipedia.org/wiki/2023_Qatar_Grand_Prix
)