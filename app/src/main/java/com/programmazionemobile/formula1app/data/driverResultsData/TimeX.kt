package com.programmazionemobile.formula1app.data.driverResultsData


import com.google.gson.annotations.SerializedName

data class TimeX(
    @SerializedName("millis")
    val millis: String, // 6163555
    @SerializedName("time")
    val time: String // +1:37.762 -> Durata Gara
)