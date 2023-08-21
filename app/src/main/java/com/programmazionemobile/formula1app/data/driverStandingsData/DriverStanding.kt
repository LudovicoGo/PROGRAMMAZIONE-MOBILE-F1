package com.programmazionemobile.formula1app.data.driverStandingsData


import com.google.gson.annotations.SerializedName

data class DriverStanding(
    @SerializedName("Constructors")
    val constructors: List<Constructor>,
    @SerializedName("Driver")
    val driver: Driver,
    @SerializedName("points")
    val points: String, // 314
    @SerializedName("position")
    val position: String, // 1
    @SerializedName("positionText")
    val positionText: String, // 1
    @SerializedName("wins")
    val wins: String // 10
)