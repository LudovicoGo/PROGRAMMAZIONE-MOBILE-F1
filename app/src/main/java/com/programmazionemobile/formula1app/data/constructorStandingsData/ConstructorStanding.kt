package com.programmazionemobile.formula1app.data.constructorStandingsData


import com.google.gson.annotations.SerializedName

data class ConstructorStanding(
    @SerializedName("Constructor")
    val `constructor`: Constructor,
    @SerializedName("points")
    val points: String, // 540
    @SerializedName("position")
    val position: String, // 1
    @SerializedName("positionText")
    val positionText: String, // 1
    @SerializedName("wins")
    val wins: String // 13
)