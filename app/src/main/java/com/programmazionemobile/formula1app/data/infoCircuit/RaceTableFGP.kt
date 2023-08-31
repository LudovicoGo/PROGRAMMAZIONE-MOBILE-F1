package com.programmazionemobile.formula1app.data.infoCircuit


import com.google.gson.annotations.SerializedName

data class RaceTableFGP(
    @SerializedName("Races")
    val races: List<FirstGP>
)