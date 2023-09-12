package com.programmazionemobile.formula1app.data.raceResultsData


import com.google.gson.annotations.SerializedName

data class FastestLap(
    @SerializedName("AverageSpeed")
    val averageSpeed: AverageSpeed,
    @SerializedName("lap")
    val lap: String, // 44
    @SerializedName("rank")
    val rank: String, // 6
    @SerializedName("Time")
    val time: Time
)