package com.programmazionemobile.formula1app.data.interfaceAPI

import com.programmazionemobile.formula1app.data.calendarData.CalendarData
import com.programmazionemobile.formula1app.data.driverResultsData.DriverResultsDataClass
import com.programmazionemobile.formula1app.data.infoCircuit.Circuit
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("{year}.json")
    suspend fun currentCalendar(@Path("year") year: String): Response<CalendarData>

    @GET("circuits/{circuitId}/results/1.json?limit=1")
    suspend fun infoCircuit(@Path("circuitId")circuitId: String): Response<Circuit>

    @GET("fastest/1/circuits/{circuitId}/results.json?limit=100")
    suspend fun fastestLap(@Path("circuitId")circuitId: String): Response<DriverResultsDataClass>
}