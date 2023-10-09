package com.programmazionemobile.formula1app.data.qualifyingData


import com.google.gson.annotations.SerializedName

data class QualifyingResult(
    @SerializedName("Constructor")
    val `constructor`: Constructor,
    @SerializedName("Driver")
    val driver: Driver,
    @SerializedName("number")
    val number: String, // 1
    @SerializedName("position")
    val position: String, // 1
    @SerializedName("Q1")
    val q1: String, // 1:29.428
    @SerializedName("Q2")
    val q2: String, // 1:27.702
    @SerializedName("Q3")
    val q3: String // 1:26.720
)