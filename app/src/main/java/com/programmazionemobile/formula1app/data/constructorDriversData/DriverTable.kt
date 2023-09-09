package com.programmazionemobile.formula1app.data.constructorDriversData


import com.google.gson.annotations.SerializedName

data class DriverTable(
    @SerializedName("constructorId")
    val constructorId: String, // alphatauri
    @SerializedName("Drivers")
    val drivers: List<Driver>,
    @SerializedName("season")
    val season: String // 2023
)