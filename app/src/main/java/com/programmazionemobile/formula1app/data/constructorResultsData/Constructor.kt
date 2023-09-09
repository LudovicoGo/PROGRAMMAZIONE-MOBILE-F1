package com.programmazionemobile.formula1app.data.constructorResultsData


import com.google.gson.annotations.SerializedName

data class Constructor(
    @SerializedName("constructorId")
    val constructorId: String, // mercedes
    @SerializedName("name")
    val name: String, // Mercedes
    @SerializedName("nationality")
    val nationality: String, // German
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/Mercedes-Benz_in_Formula_One
)