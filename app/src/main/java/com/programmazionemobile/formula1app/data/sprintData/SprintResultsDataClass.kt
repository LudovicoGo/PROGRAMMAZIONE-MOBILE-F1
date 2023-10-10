package com.programmazionemobile.formula1app.data.sprintData


import com.google.gson.annotations.SerializedName

data class SprintResultsDataClass(
    @SerializedName("MRData")
    val mRData: MRData
)