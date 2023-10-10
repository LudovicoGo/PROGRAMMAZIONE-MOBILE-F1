package com.programmazionemobile.formula1app.data.sprintData


import com.google.gson.annotations.SerializedName

data class Circuit(
    @SerializedName("circuitId")
    val circuitId: String, // losail
    @SerializedName("circuitName")
    val circuitName: String, // Losail International Circuit
    @SerializedName("Location")
    val location: Location,
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/Losail_International_Circuit
)