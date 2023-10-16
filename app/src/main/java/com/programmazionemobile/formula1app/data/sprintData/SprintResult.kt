package com.programmazionemobile.formula1app.data.sprintData


import com.google.gson.annotations.SerializedName

data class SprintResult(
    @SerializedName("Constructor")
    val `constructor`: Constructor,
    @SerializedName("Driver")
    val driver: Driver,
    @SerializedName("FastestLap")
    val fastestLap: FastestLap,
    @SerializedName("grid")
    val grid: String, // 1
    @SerializedName("laps")
    val laps: String, // 19
    @SerializedName("number")
    val number: String, // 81
    @SerializedName("points")
    val points: String, // 8
    @SerializedName("position")
    val position: String, // 1
    @SerializedName("positionText")
    val positionText: String, // 1
    @SerializedName("status")
    val status: String, // Finished
    @SerializedName("Time")
    val time: TimeX
)