package com.programmazionemobile.formula1app.data.raceResultsData


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("country")
    val country: String, // Bahrain
    @SerializedName("lat")
    val lat: String, // 26.0325
    @SerializedName("locality")
    val locality: String, // Sakhir
    @SerializedName("long")
    val long: String // 50.5106
)