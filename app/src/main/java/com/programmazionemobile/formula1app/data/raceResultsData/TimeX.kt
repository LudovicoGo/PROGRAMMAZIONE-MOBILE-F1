package com.programmazionemobile.formula1app.data.raceResultsData


import com.google.gson.annotations.SerializedName

data class TimeX(
    @SerializedName("millis")
    val millis: String, // 5636736
    @SerializedName("time")
    val time: String // 1:33:56.736
)