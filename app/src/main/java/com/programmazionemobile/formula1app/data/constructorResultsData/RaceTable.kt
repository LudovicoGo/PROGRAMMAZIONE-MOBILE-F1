package com.programmazionemobile.formula1app.data.constructorResultsData


import com.google.gson.annotations.SerializedName

data class RaceTable(
    @SerializedName("constructorId")
    val constructorId: String, // mercedes
    @SerializedName("Races")
    val races: List<Race>
)