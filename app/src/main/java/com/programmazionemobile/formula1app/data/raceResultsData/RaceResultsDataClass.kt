package com.programmazionemobile.formula1app.data.raceResultsData


import com.google.gson.annotations.SerializedName

data class RaceResultsDataClass(
    @SerializedName("MRData")
    val mRData: MRData
)