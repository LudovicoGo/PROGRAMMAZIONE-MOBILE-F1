package com.programmazionemobile.formula1app.data.driverResultsData


import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("country")
    val country: String, // Australia
    @SerializedName("lat")
    val lat: String, // -37.8497
    @SerializedName("locality")
    val locality: String, // Melbourne
    @SerializedName("long")
    val long: String // 144.968
)