package com.programmazionemobile.formula1app.data.qualifyingData


import com.google.gson.annotations.SerializedName

data class Circuit(
    @SerializedName("circuitId")
    val circuitId: String, // silverstone
    @SerializedName("circuitName")
    val circuitName: String, // Silverstone Circuit
    @SerializedName("Location")
    val location: Location,
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/Silverstone_Circuit
)