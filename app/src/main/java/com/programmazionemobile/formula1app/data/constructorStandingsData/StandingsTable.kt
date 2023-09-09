package com.programmazionemobile.formula1app.data.constructorStandingsData


import com.google.gson.annotations.SerializedName

data class StandingsTable(
    @SerializedName("season")
    val season: String, // 2023
    @SerializedName("StandingsLists")
    val standingsLists: List<StandingsLists>
)