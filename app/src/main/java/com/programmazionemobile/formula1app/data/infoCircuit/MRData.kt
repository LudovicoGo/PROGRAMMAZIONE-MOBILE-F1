package com.programmazionemobile.formula1app.data.infoCircuit


import com.google.gson.annotations.SerializedName

data class MRData(
    @SerializedName("limit")
    val limit: String,
    @SerializedName("offset")
    val offset: String,
    @SerializedName("RaceTable")
    val raceTable: RaceTableFGP,
    @SerializedName("series")
    val series: String,
    @SerializedName("total")
    val total: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("xmlns")
    val xmlns: String
)