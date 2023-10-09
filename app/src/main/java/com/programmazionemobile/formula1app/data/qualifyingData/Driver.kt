package com.programmazionemobile.formula1app.data.qualifyingData


import com.google.gson.annotations.SerializedName

data class Driver(
    @SerializedName("code")
    val code: String, // VER
    @SerializedName("dateOfBirth")
    val dateOfBirth: String, // 1997-09-30
    @SerializedName("driverId")
    val driverId: String, // max_verstappen
    @SerializedName("familyName")
    val familyName: String, // Verstappen
    @SerializedName("givenName")
    val givenName: String, // Max
    @SerializedName("nationality")
    val nationality: String, // Dutch
    @SerializedName("permanentNumber")
    val permanentNumber: String, // 33
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/Max_Verstappen
)