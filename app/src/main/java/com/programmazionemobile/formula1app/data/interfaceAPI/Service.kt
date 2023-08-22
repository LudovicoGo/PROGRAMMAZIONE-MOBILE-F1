package com.programmazionemobile.formula1app.data.interfaceAPI

import com.programmazionemobile.formula1app.data.calendarData.calendarData
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("current.json")
    suspend fun currentCalendar(): Response<calendarData>
}