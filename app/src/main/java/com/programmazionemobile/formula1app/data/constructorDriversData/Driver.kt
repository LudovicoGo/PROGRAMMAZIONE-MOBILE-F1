package com.programmazionemobile.formula1app.data.constructorDriversData


import com.google.gson.annotations.SerializedName

data class Driver(
    @SerializedName("code")
    val code: String, // DEV
    @SerializedName("dateOfBirth")
    val dateOfBirth: String, // 1995-02-06
    @SerializedName("driverId")
    val driverId: String, // de_vries
    @SerializedName("familyName")
    val familyName: String, // de Vries
    @SerializedName("givenName")
    val givenName: String, // Nyck
    @SerializedName("nationality")
    val nationality: String, // Dutch
    @SerializedName("permanentNumber")
    val permanentNumber: String, // 21
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/Nyck_de_Vries
)