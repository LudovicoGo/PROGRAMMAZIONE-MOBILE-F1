package com.programmazionemobile.formula1app.data.driverResultsData


import com.google.gson.annotations.SerializedName

data class Constructor(
    @SerializedName("constructorId")
    val constructorId: String, // toro_rosso
    @SerializedName("name")
    val name: String, // Toro Rosso
    @SerializedName("nationality")
    val nationality: String, // Italian
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/Scuderia_Toro_Rosso
)