package com.programmazionemobile.formula1app.data.driverResultsData


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("Constructor")
    val `constructor`: Constructor,
    @SerializedName("Driver")
    val driver: Driver,
    @SerializedName("FastestLap")
    val fastestLap: FastestLap,
    @SerializedName("grid")
    val grid: String, // 11
    @SerializedName("laps")
    val laps: String, // 32
    @SerializedName("number")
    val number: String, // 33
    @SerializedName("points")
    val points: String, // 0
    @SerializedName("position")
    val position: String, // 13
    @SerializedName("positionText")
    val positionText: String, // R
    @SerializedName("status")
    val status: String, // Engine
    @SerializedName("Time")
    val time: TimeX
)