package com.programmazionemobile.formula1app.data.qualifyingData


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("country")
    val country: String, // UK
    @SerializedName("lat")
    val lat: String, // 52.0786
    @SerializedName("locality")
    val locality: String, // Silverstone
    @SerializedName("long")
    val long: String // -1.01694
)