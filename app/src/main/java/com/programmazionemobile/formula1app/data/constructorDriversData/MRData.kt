package com.programmazionemobile.formula1app.data.constructorDriversData


import com.google.gson.annotations.SerializedName

data class MRData(
    @SerializedName("DriverTable")
    val driverTable: DriverTable,
    @SerializedName("limit")
    val limit: String, // 30
    @SerializedName("offset")
    val offset: String, // 0
    @SerializedName("series")
    val series: String, // f1
    @SerializedName("total")
    val total: String, // 4
    @SerializedName("url")
    val url: String, // http://ergast.com/api/f1/2023/constructors/alphatauri/drivers.json
    @SerializedName("xmlns")
    val xmlns: String // http://ergast.com/mrd/1.5
)