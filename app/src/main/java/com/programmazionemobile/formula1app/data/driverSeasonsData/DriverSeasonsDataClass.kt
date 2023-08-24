package com.programmazionemobile.formula1app.data.driverSeasonsData


import com.google.gson.annotations.SerializedName

data class DriverSeasonsDataClass(
    @SerializedName("MRData")
    val mRData: MRData
)

data class MRData(
    @SerializedName("limit")
    val limit: String, // 30
    @SerializedName("offset")
    val offset: String, // 0
    @SerializedName("SeasonTable")
    val seasonTable: SeasonTable,
    @SerializedName("series")
    val series: String, // f1
    @SerializedName("total")
    val total: String, // 16
    @SerializedName("url")
    val url: String, // http://ergast.com/api/f1/drivers/vettel/seasons.json
    @SerializedName("xmlns")
    val xmlns: String // http://ergast.com/mrd/1.5
)

data class Season(
    @SerializedName("season")
    val season: String, // 2007
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/2007_Formula_One_season
)

data class SeasonTable(
    @SerializedName("driverId")
    val driverId: String, // vettel
    @SerializedName("Seasons")
    val seasons: List<Season>
)