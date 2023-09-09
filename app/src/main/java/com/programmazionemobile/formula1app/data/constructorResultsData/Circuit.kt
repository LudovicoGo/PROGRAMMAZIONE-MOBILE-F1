package com.programmazionemobile.formula1app.data.constructorResultsData


import com.google.gson.annotations.SerializedName

data class Circuit(
    @SerializedName("circuitId")
    val circuitId: String, // reims
    @SerializedName("circuitName")
    val circuitName: String, // Reims-Gueux
    @SerializedName("Location")
    val location: Location,
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/Reims-Gueux
)