package com.programmazionemobile.formula1app.data.interfaceAPI

import android.icu.util.Calendar
import com.programmazionemobile.formula1app.data.constructorDriversData.ConstructorDriversDataClass
import com.programmazionemobile.formula1app.data.constructorResultsData.ConstructorResultsDataClass
import com.programmazionemobile.formula1app.data.constructorStandingsData.ConstructorStandingsDataClass
import com.programmazionemobile.formula1app.data.driverResultsData.DriverResultsDataClass
import com.programmazionemobile.formula1app.data.driverSeasonsData.DriverSeasonsDataClass
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStandingsDataClass
import com.programmazionemobile.formula1app.data.qualifyingData.QualifyingDataClass
import com.programmazionemobile.formula1app.data.raceResultsData.RaceResultsDataClass
import com.programmazionemobile.formula1app.data.sprintData.SprintResultsDataClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.Year

interface ErgastApi {
    @GET("{year}/constructorStandings.json")
    suspend fun getConstructorStandings(@Path("year") year: String): Response<ConstructorStandingsDataClass>

    @GET("{year}/driverStandings.json")
    suspend fun getDriverStandings(@Path("year") year: String): Response<DriverStandingsDataClass>

    @GET("/driverStandings.json")
    suspend fun getAllDriverStandings(): Response<DriverStandingsDataClass>

    @GET("drivers/{driverID}/driverStandings.json?limit=1000")
    suspend fun getSingleDriverStandings(@Path("driverID") driverID: String): Response<DriverStandingsDataClass>


    @GET("drivers/{driverID}/results.json?limit=1000")
    suspend fun getDriverResults(@Path("driverID") driverID: String): Response<DriverResultsDataClass>

    @GET("constructors/{constructorID}/results.json")
    suspend fun getConstructorResults(@Path("constructorID") constructorID: String, @Query("limit") limit: Int = 1000, @Query("offset") offset: String): Response<ConstructorResultsDataClass>

    @GET("constructorStandings/1.json?limit=1000")
    suspend fun getConstructorChampions(): Response<ConstructorStandingsDataClass>

    @GET("{year}/constructors/{constructorID}/drivers.json")
    suspend fun getConstructorDrivers(@Path("constructorID") constructorID: String, @Path("year") year: String): Response<ConstructorDriversDataClass>



    @GET("{year}/{round}/results.json")
    suspend fun getRaceResults(@Path("year") year: String, @Path("round") round: String): Response<RaceResultsDataClass>
    @GET("{year}/{round}/sprint.json")
    suspend fun getSprintRaceResults(@Path("year") year: String, @Path("round") round: String): Response<SprintResultsDataClass>
    @GET("{year}/{round}/qualifying.json")
    suspend fun getQualifyingResults(@Path("year") year: String, @Path("round") round: String): Response<QualifyingDataClass>
}