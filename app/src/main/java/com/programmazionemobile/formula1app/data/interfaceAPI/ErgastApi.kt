package com.programmazionemobile.formula1app.data.interfaceAPI

import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStandingsDataClass
import retrofit2.Response
import retrofit2.http.GET

interface ErgastApi {
/*
    @GET("2023/drivers.json")
    fun getDrivers(): Response<ErgastF1DataClass>
*/

    @GET("2023/driverStandings.json")
    suspend fun getDriverStandings(): Response<DriverStandingsDataClass>


}