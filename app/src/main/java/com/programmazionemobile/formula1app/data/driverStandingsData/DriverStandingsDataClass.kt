package com.programmazionemobile.formula1app.data.driverStandingsData


import com.google.gson.annotations.SerializedName

data class DriverStandingsDataClass(
    @SerializedName("MRData")
    val mRData: MRData
)