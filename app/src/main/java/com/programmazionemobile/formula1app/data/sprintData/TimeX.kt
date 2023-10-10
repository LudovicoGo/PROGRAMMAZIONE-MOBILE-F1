package com.programmazionemobile.formula1app.data.sprintData


import com.google.gson.annotations.SerializedName

data class TimeX(
    @SerializedName("millis")
    val millis: String, // 2101297
    @SerializedName("time")
    val time: String // 35:01.297
)