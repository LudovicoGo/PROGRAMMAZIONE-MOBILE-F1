package com.programmazionemobile.formula1app.data.sprintData


import com.google.gson.annotations.SerializedName

data class FastestLap(
    @SerializedName("lap")
    val lap: String, // 17
    @SerializedName("Time")
    val time: Time
)