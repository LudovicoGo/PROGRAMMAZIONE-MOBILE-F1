package com.programmazionemobile.formula1app.data.calendarData


import com.google.gson.annotations.SerializedName

data class Sprint(
    @SerializedName("date")
    val date: String,
    @SerializedName("time")
    val time: String
)