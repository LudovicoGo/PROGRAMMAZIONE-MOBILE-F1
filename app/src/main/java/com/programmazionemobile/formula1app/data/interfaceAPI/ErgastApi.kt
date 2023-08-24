package com.programmazionemobile.formula1app.data.interfaceAPI

import com.programmazionemobile.formula1app.data.driverResultsData.DriverResultsDataClass
import com.programmazionemobile.formula1app.data.driverSeasonsData.DriverSeasonsDataClass
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStandingsDataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ErgastApi {
/*
    @GET("2023/drivers.json")
    fun getDrivers(): Response<ErgastF1DataClass>
*/

    @GET("2023/driverStandings.json")
    suspend fun getDriverStandings(): Response<DriverStandingsDataClass>
    @GET("drivers/{driverID}/seasons.json")
    suspend fun getDriverSeasons(@Path("driverID")driverID: String): Response<DriverSeasonsDataClass>
    @GET("drivers/{driverID}/results.json?limit=1000")
    suspend fun getDriverResults(@Path("driverID")driverID: String): Response<DriverResultsDataClass>





}