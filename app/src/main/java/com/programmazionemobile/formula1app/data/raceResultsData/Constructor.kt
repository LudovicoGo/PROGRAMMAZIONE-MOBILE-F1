package com.programmazionemobile.formula1app.data.raceResultsData


import com.google.gson.annotations.SerializedName

data class Constructor(
    @SerializedName("constructorId")
    val constructorId: String, // red_bull
    @SerializedName("name")
    val name: String, // Red Bull
    @SerializedName("nationality")
    val nationality: String, // Austrian
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/Red_Bull_Racing
)