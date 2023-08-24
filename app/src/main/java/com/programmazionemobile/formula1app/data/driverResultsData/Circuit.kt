package com.programmazionemobile.formula1app.data.driverResultsData


import com.google.gson.annotations.SerializedName

data class Circuit(
    @SerializedName("circuitId")
    val circuitId: String, // albert_park
    @SerializedName("circuitName")
    val circuitName: String, // Albert Park Grand Prix Circuit
    @SerializedName("Location")
    val location: Location,
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit
)