package com.programmazionemobile.formula1app.data.driverResultsData


import com.google.gson.annotations.SerializedName

data class RaceTable(
    @SerializedName("driverId")
    val driverId: String, // max_verstappen
    @SerializedName("Races")
    val races: List<Race>
)