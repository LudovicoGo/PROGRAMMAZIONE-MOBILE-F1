package com.programmazionemobile.formula1app.data.driverStandingsData


import com.google.gson.annotations.SerializedName

data class MRData(
    @SerializedName("limit")
    val limit: String, // 30
    @SerializedName("offset")
    val offset: String, // 0
    @SerializedName("series")
    val series: String, // f1
    @SerializedName("StandingsTable")
    val standingsTable: StandingsTable,
    @SerializedName("total")
    val total: String, // 21
    @SerializedName("url")
    val url: String, // http://ergast.com/api/f1/2023/driverstandings.json
    @SerializedName("xmlns")
    val xmlns: String // http://ergast.com/mrd/1.5
)