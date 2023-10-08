package com.programmazionemobile.formula1app.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.driverResultsData.FastestLap
import com.programmazionemobile.formula1app.data.interfaceAPI.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Date

class CircuitInfoViewModel: ViewModel() {
    private val BASE_URL = "https://ergast.com/api/f1/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Service::class.java)

    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean>
        get() = _isInternetConnected

    init{
        _isInternetConnected.value = true
    }

    fun getFirstGPLiveData(circuitID: String): MutableLiveData<String?> {

        val dataFirstGP = MutableLiveData<String?>()

        viewModelScope.launch {
            try {
                val response = api.infoCircuit(circuitID)
                _isInternetConnected.value = true
                println(response)

                if (response.isSuccessful) {
                    val races = response.body()?.mRData?.raceTable?.races
                    if (!races.isNullOrEmpty()) {
                        val season = races.firstOrNull()?.season
                        if (season != null) {
                            dataFirstGP.value = season
                        } else {
                            dataFirstGP.value = "Season data not available"
                        }
                    } else {
                        dataFirstGP.value = "No race data available"
                    }
                } else {
                    dataFirstGP.value = "API request failed"
                }
            } catch (e: Exception) {
                // Handle any exceptions that occur during the API call here
//                dataFirstGP.value = "Error occurred: ${e.message}"
                dataFirstGP.value = "-"
                _isInternetConnected.value = false
            }
        }

        return dataFirstGP
    }


    fun getFastestLap(circuitID: String): LiveData<String?> {
        val fastestLap = MutableLiveData<String?>()

        viewModelScope.launch {
            try {
                if (getFirstGPLiveData(circuitID).value != "2023"){
                    val response = api.fastestLap(circuitID)

                    if (response.isSuccessful) {
                        var fl: String? = null

                        for (race in response.body()?.mRData?.raceTable?.races.orEmpty()) {
                            val results = race.results
                            if (results.isNotEmpty()) {
                                val currentFastestLapTime = results.first().fastestLap.time.time
                                if (fl == null || (currentFastestLapTime < fl)) {
                                    fl = currentFastestLapTime
                                }
                            }
                        }

                        if (fl == null) {
                            fastestLap.value = "Dati non disponibili"
                        } else {
                            fastestLap.value = fl
                        }
                    } else {
                        fastestLap.value = "Errore nella richiesta"
                    }
                } else {
                    fastestLap.value = "Dati non disponibili"
                }
            } catch (e: Exception) {
//                fastestLap.value = "Errore: ${e.message}"
                fastestLap.value = "-"
            }
        }

        return fastestLap
    }


    fun getDriverFastLap(circuitID: String): MutableLiveData<String?> {

        val giroVeloce = MutableLiveData<String?>()

        viewModelScope.launch {
            try {
                if (getFirstGPLiveData(circuitID).value != "2023"){

                    val response = api.fastestLap(circuitID)

                    if (response.isSuccessful) {
                        var fl: String? = null
                        var pilota: String? = null

                        for (race in response.body()?.mRData?.raceTable?.races.orEmpty()) {
                            val results = race.results
                            if (results.isNotEmpty()) {
                                val currentFastestLapTime = results.first().fastestLap.time.time
                                if (fl == null || (currentFastestLapTime < fl)) {
                                    fl = currentFastestLapTime
                                    pilota = '(' + race.results.first().driver.givenName + ' ' +
                                            race.results.first().driver.familyName + ' ' +
                                            race.season + ')'
                                }
                            }
                        }

                        if (fl == null) {
                            giroVeloce.value = ""
                        } else {
                            giroVeloce.value = pilota
                        }
                    } else {
                        giroVeloce.value = "Errore nella richiesta"
                    }

                }
            } catch (e: Exception) {
//                giroVeloce.value = "Errore: ${e.message}"
                giroVeloce.value = "-"
            }
        }

        return giroVeloce
    }

    fun setInternetConnectionStatus(isConnected: Boolean) {
        _isInternetConnected.value = isConnected
    }
}