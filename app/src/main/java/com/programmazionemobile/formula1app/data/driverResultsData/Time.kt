package com.programmazionemobile.formula1app.data.driverResultsData


import com.google.gson.annotations.SerializedName

data class Time(
    @SerializedName("time")
    val time: String // 1:34.295 -> Giro Veloce
)