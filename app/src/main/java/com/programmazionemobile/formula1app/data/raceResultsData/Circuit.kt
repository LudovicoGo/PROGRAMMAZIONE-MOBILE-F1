package com.programmazionemobile.formula1app.data.raceResultsData


import com.google.gson.annotations.SerializedName

data class Circuit(
    @SerializedName("circuitId")
    val circuitId: String, // bahrain
    @SerializedName("circuitName")
    val circuitName: String, // Bahrain International Circuit
    @SerializedName("Location")
    val location: Location,
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/Bahrain_International_Circuit
)