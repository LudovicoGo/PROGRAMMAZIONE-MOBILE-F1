package com.programmazionemobile.formula1app.data.constructorResultsData


import com.google.gson.annotations.SerializedName

data class TimeX(
    @SerializedName("millis")
    val millis: String, // 9767900
    @SerializedName("time")
    val time: String // 2:42:47.9
)