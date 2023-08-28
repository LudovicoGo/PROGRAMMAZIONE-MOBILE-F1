package com.programmazionemobile.formula1app.data.interfaceAPI

import android.icu.util.Calendar
import com.programmazionemobile.formula1app.data.driverResultsData.DriverResultsDataClass
import com.programmazionemobile.formula1app.data.driverSeasonsData.DriverSeasonsDataClass
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStandingsDataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.Year

interface ErgastApi {
    @GET("{year}/driverStandings.json")
    suspend fun getDriverStandings(@Path("year") year: String): Response<DriverStandingsDataClass>

    @GET("/driverStandings.json")
    suspend fun getAllDriverStandings(): Response<DriverStandingsDataClass>

    @GET("drivers/{driverID}/driverStandings.json?limit=1000")
    suspend fun getSingleDriverStandings(@Path("driverID") driverID: String): Response<DriverStandingsDataClass>


    /* @GET("drivers/{driverID}/seasons.json")
     suspend fun getDriverSeasons(@Path("driverID")driverID: String): Response<DriverSeasonsDataClass>*/

    @GET("drivers/{driverID}/results.json?limit=1000")
    suspend fun getDriverResults(@Path("driverID") driverID: String): Response<DriverResultsDataClass>
}