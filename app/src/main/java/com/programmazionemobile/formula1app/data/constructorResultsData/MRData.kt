package com.programmazionemobile.formula1app.data.constructorResultsData


import com.google.gson.annotations.SerializedName

data class MRData(
    @SerializedName("limit")
    val limit: String, // 1000
    @SerializedName("offset")
    val offset: String, // 0
    @SerializedName("RaceTable")
    val raceTable: RaceTable,
    @SerializedName("series")
    val series: String, // f1
    @SerializedName("total")
    val total: String, // 588
    @SerializedName("url")
    val url: String, // http://ergast.com/api/f1/constructors/mercedes/results.json
    @SerializedName("xmlns")
    val xmlns: String // http://ergast.com/mrd/1.5
)