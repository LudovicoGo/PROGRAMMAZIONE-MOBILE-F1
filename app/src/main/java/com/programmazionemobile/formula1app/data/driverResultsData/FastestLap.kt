package com.programmazionemobile.formula1app.data.driverResultsData


import com.google.gson.annotations.SerializedName

data class FastestLap(
    @SerializedName("AverageSpeed")
    val averageSpeed: AverageSpeed,
    @SerializedName("lap")
    val lap: String, // 30
    @SerializedName("rank")
    val rank: String, // 13
    @SerializedName("Time")
    val time: Time
)