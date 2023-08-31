package com.programmazionemobile.formula1app.data.infoCircuit

import com.google.gson.annotations.SerializedName

data class FirstGP(
    @SerializedName("season")
    val season: String,
    @SerializedName("round")
    val round: String,
    @SerializedName("raceName")
    val raceName: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("url")
    val url: String
)
