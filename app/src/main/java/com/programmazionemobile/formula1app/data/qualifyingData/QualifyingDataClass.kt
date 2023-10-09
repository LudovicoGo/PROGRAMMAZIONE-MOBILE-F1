package com.programmazionemobile.formula1app.data.qualifyingData


import com.google.gson.annotations.SerializedName

data class QualifyingDataClass(
    @SerializedName("MRData")
    val mRData: MRData
)