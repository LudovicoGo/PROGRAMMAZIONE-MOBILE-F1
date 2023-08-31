package com.programmazionemobile.formula1app.data.infoCircuit

import com.google.gson.annotations.SerializedName

data class Circuit(
    @SerializedName("MRData")
    val mRData: MRData
)