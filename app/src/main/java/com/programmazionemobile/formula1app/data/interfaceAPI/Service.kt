package com.programmazionemobile.formula1app.data.interfaceAPI

import com.programmazionemobile.formula1app.data.calendarData.Circuit
import com.programmazionemobile.formula1app.data.calendarData.calendarData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("current.json")
    suspend fun currentCalendar(): Response<calendarData>

    @GET("circuits/{circuitId}.json")
    suspend fun infoCircuit(@Path("circuitId")circuitId: String): Response<Circuit>
}