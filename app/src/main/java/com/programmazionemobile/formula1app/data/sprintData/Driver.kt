package com.programmazionemobile.formula1app.data.sprintData


import com.google.gson.annotations.SerializedName

data class Driver(
    @SerializedName("code")
    val code: String, // PIA
    @SerializedName("dateOfBirth")
    val dateOfBirth: String, // 2001-04-06
    @SerializedName("driverId")
    val driverId: String, // piastri
    @SerializedName("familyName")
    val familyName: String, // Piastri
    @SerializedName("givenName")
    val givenName: String, // Oscar
    @SerializedName("nationality")
    val nationality: String, // Australian
    @SerializedName("permanentNumber")
    val permanentNumber: String, // 81
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/Oscar_Piastri
)