package com.programmazionemobile.formula1app

import com.programmazionemobile.formula1app.data.CalendarData
import retrofit2.http.GET

interface Service {
    @GET("current")
    suspend fun currentCalendar(): List<CalendarFragment>
}