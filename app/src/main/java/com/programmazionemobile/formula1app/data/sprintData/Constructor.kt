package com.programmazionemobile.formula1app.data.sprintData


import com.google.gson.annotations.SerializedName

data class Constructor(
    @SerializedName("constructorId")
    val constructorId: String, // mclaren
    @SerializedName("name")
    val name: String, // McLaren
    @SerializedName("nationality")
    val nationality: String, // British
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/McLaren
)