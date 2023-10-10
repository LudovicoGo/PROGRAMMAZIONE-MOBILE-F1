package com.programmazionemobile.formula1app.data.sprintData


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("country")
    val country: String, // Qatar
    @SerializedName("lat")
    val lat: String, // 25.49
    @SerializedName("locality")
    val locality: String, // Al Daayen
    @SerializedName("long")
    val long: String // 51.4542
)