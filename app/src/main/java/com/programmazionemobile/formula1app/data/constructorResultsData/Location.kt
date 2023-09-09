package com.programmazionemobile.formula1app.data.constructorResultsData


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("country")
    val country: String, // France
    @SerializedName("lat")
    val lat: String, // 49.2542
    @SerializedName("locality")
    val locality: String, // Reims
    @SerializedName("long")
    val long: String // 3.93083
)