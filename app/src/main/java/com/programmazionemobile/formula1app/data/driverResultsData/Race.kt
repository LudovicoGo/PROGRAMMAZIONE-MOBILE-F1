package com.programmazionemobile.formula1app.data.driverResultsData


import com.google.gson.annotations.SerializedName

data class Race(
    @SerializedName("Circuit")
    val circuit: Circuit,
    @SerializedName("date")
    val date: String, // 2015-03-15
    @SerializedName("raceName")
    val raceName: String, // Australian Grand Prix
    @SerializedName("Results")
    val results: List<Result>,
    @SerializedName("round")
    val round: String, // 1
    @SerializedName("season")
    val season: String, // 2015
    @SerializedName("time")
    val time: String, // 05:00:00Z
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/2015_Australian_Grand_Prix
)