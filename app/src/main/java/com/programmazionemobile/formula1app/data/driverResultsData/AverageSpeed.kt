package com.programmazionemobile.formula1app.data.driverResultsData


import com.google.gson.annotations.SerializedName

data class AverageSpeed(
    @SerializedName("speed")
    val speed: String, // 202.458
    @SerializedName("units")
    val units: String // kph
)