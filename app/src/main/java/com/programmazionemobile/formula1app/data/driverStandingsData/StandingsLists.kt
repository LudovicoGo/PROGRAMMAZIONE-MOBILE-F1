package com.programmazionemobile.formula1app.data.driverStandingsData


import com.google.gson.annotations.SerializedName

data class StandingsLists(
    @SerializedName("DriverStandings")
    val driverStandings: List<DriverStanding>,
    @SerializedName("round")
    val round: String, // 12
    @SerializedName("season")
    val season: String // 2023
)