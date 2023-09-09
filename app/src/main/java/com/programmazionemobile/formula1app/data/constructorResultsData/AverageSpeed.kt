package com.programmazionemobile.formula1app.data.constructorResultsData


import com.google.gson.annotations.SerializedName

data class AverageSpeed(
    @SerializedName("speed")
    val speed: String, // 188.599
    @SerializedName("units")
    val units: String // kph
)