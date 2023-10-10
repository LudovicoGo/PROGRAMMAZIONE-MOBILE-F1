package com.programmazionemobile.formula1app.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.constructorDriversData.Driver
import com.programmazionemobile.formula1app.data.constructorResultsData.Race
import com.programmazionemobile.formula1app.data.driverResultsData.Result
import com.programmazionemobile.formula1app.data.interfaceAPI.ErgastApi
import com.programmazionemobile.formula1app.data.raceResultsData.AverageSpeed
import com.programmazionemobile.formula1app.data.raceResultsData.Constructor
import com.programmazionemobile.formula1app.data.raceResultsData.FastestLap
import com.programmazionemobile.formula1app.data.raceResultsData.Time
import com.programmazionemobile.formula1app.data.raceResultsData.TimeX
import com.programmazionemobile.formula1app.data.sprintData.SprintResult
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.round

class RaceResultsViewModel : ViewModel() {
    private val BASE_URL = "https://ergast.com/api/f1/"
    private val TAG: String = "CHECK_RESPONSE"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ErgastApi::class.java)

    private val _raceResults =
        MutableLiveData<List<com.programmazionemobile.formula1app.data.raceResultsData.Result>>()

    val raceResults: LiveData<List<com.programmazionemobile.formula1app.data.raceResultsData.Result>>
        get() = _raceResults


    private val _sprintResults =
        MutableLiveData<List<SprintResult>>()

    val sprintResults: LiveData<List<SprintResult>>
        get() = _sprintResults


    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean>
        get() = _isInternetConnected

    init {
        _isInternetConnected.value = true
    }

    fun getRaceResults(
        year: String,
        round: String
    ) {//per prendere il numero di titoli costruttori vinti
        viewModelScope.launch {
            try {

                val results: MutableList<com.programmazionemobile.formula1app.data.raceResultsData.Result> =
                    mutableListOf()
                val response = api.getRaceResults(year, round)
                _isInternetConnected.value = true
                println(response.body().toString() + year + round)

                if (response.isSuccessful) {
//                    Log.d(TAG, response.body().toString())
//                    println(response.body().toString())

                    if (response.body() != null) {
                        for (race in response.body()!!.mRData.raceTable.races) {
                            for (result in race.results) {
//                                Log.d(TAG, "${result.driver}, ${result.position}, ${result.points}")
                                results += result
                            }
                        }
//                        Log.d(TAG, response.body().toString())
                    }

                    _raceResults.value = results
                }

            } catch (e: Exception) {
                Log.d(TAG, e.toString())
                _isInternetConnected.value = false
            }

        }
    }

    fun getsSprintResults(
        year: String,
        round: String
    ) {//per prendere il numero di titoli costruttori vinti
        viewModelScope.launch {
            try {

                val sprintResults: MutableList<SprintResult> =
                    mutableListOf()
                val response = api.getSprintRaceResults(year, round)
                _isInternetConnected.value = true
                println(response.body().toString() + year + round)

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        for (race in response.body()!!.mRData.raceTable.races) {
                            for (result in race.sprintResults) {
//                                Log.d(TAG, "${result.driver}, ${result.position}, ${result.points}")
                               sprintResults += result
                            }
                        }
//                        Log.d(TAG, response.body().toString())
                    }

                    _sprintResults.value = sprintResults
                }

            } catch (e: Exception) {
                Log.d(TAG, e.toString())
                _isInternetConnected.value = false
            }

        }
    }
    /*fun getsSprintResults(
        year: String,
        round: String
    ) {//per prendere il numero di titoli costruttori vinti
        viewModelScope.launch {
            try {

                val sprintResults: MutableList<com.programmazionemobile.formula1app.data.raceResultsData.Result> =
                    mutableListOf()
                val response = api.getSprintRaceResults(year, round)
                _isInternetConnected.value = true
                println(response.body().toString() + year + round)

                if (response.isSuccessful) {
                    if (response.body() != null) {
                        for (race in response.body()!!.mRData.raceTable.races) {
                            for (result in race.sprintResults) {
//                                Log.d(TAG, "${result.driver}, ${result.position}, ${result.points}")
                                var raceResult =
                                    com.programmazionemobile.formula1app.data.raceResultsData.Result(
                                        Constructor(result.constructor.constructorId,result.constructor.name,result.constructor.nationality,result.constructor.url),
                                        com.programmazionemobile.formula1app.data.raceResultsData.Driver(result.driver.code,result.driver.dateOfBirth,result.driver.driverId,result.driver.familyName,result.driver.givenName,result.driver.nationality,result.driver.permanentNumber),
                                        FastestLap(AverageSpeed("",""),result.fastestLap.lap, "", Time("")),
                                        result.grid,
                                        result.laps,
                                        result.number,
                                        result.points,
                                        result.position,
                                        result.positionText,
                                        result.status,
                                        TimeX(result.time.millis, result.time.time)
                                    )
                                sprintResults += raceResult
                            }
                        }
//                        Log.d(TAG, response.body().toString())
                    }

                    _sprintResults.value = sprintResults
                }

            } catch (e: Exception) {
                Log.d(TAG, e.toString())
                _isInternetConnected.value = false
            }

        }
    }*/
    fun setInternetConnectionStatus(isConnected: Boolean) {
        _isInternetConnected.value = isConnected
    }
}