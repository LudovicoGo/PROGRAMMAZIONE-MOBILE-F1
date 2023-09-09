package com.programmazionemobile.formula1app.data.constructorResultsData


import com.google.gson.annotations.SerializedName

data class Race(
    @SerializedName("Circuit")
    val circuit: Circuit,
    @SerializedName("date")
    val date: String, // 1954-07-04
    @SerializedName("raceName")
    val raceName: String, // French Grand Prix
    @SerializedName("Results")
    val results: List<Result>,
    @SerializedName("round")
    val round: String, // 4
    @SerializedName("season")
    val season: String, // 1954
    @SerializedName("time")
    val time: String, // 12:00:00Z
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/1954_French_Grand_Prix
)