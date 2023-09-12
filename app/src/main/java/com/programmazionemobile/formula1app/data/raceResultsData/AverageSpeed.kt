package com.programmazionemobile.formula1app.data.raceResultsData


import com.google.gson.annotations.SerializedName

data class AverageSpeed(
    @SerializedName("speed")
    val speed: String, // 202.452
    @SerializedName("units")
    val units: String // kph
)