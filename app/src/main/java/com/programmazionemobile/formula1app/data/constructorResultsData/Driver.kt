package com.programmazionemobile.formula1app.data.constructorResultsData


import com.google.gson.annotations.SerializedName

data class Driver(
    @SerializedName("code")
    val code: String, // ROS
    @SerializedName("dateOfBirth")
    val dateOfBirth: String, // 1911-06-24
    @SerializedName("driverId")
    val driverId: String, // fangio
    @SerializedName("familyName")
    val familyName: String, // Fangio
    @SerializedName("givenName")
    val givenName: String, // Juan
    @SerializedName("nationality")
    val nationality: String, // Argentine
    @SerializedName("permanentNumber")
    val permanentNumber: String, // 6
    @SerializedName("url")
    val url: String // http://en.wikipedia.org/wiki/Juan_Manuel_Fangio
)