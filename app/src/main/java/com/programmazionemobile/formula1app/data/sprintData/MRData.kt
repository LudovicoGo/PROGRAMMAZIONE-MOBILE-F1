package com.programmazionemobile.formula1app.data.sprintData


import com.google.gson.annotations.SerializedName

data class MRData(
    @SerializedName("limit")
    val limit: String, // 30
    @SerializedName("offset")
    val offset: String, // 0
    @SerializedName("RaceTable")
    val raceTable: RaceTable,
    @SerializedName("series")
    val series: String, // f1
    @SerializedName("total")
    val total: String, // 20
    @SerializedName("url")
    val url: String, // http://ergast.com/api/f1/2023/17/sprint.json
    @SerializedName("xmlns")
    val xmlns: String // http://ergast.com/mrd/1.5
)