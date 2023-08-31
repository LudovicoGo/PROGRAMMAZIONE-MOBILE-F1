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

    fun getFirstGPLiveData(circuitID: String): MutableLiveData<String?> {

        val dataFirstGP = MutableLiveData<String?>()

        viewModelScope.launch {
            val response = api.infoCircuit(circuitID)
            println(response)

            if (response.isSuccessful) {
                val season = response.body()?.mRData?.raceTable?.races?.first()?.season
                dataFirstGP.value = season
            } else {
                dataFirstGP.value = "Dati non disponibili"
            }
        }

        return dataFirstGP
    }

    fun getFastestLap(circuitID: String): LiveData<String?> {
        val fastestLap = MutableLiveData<String?>()

        viewModelScope.launch {
            try {
                if (getFirstGPLiveData(circuitID).toString() != "2023"){
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
                fastestLap.value = "Errore: ${e.message}"
            }
        }

        return fastestLap
    }


    fun getDriverFastLap(circuitID: String): MutableLiveData<String?> {

        val giroVeloce = MutableLiveData<String?>()

        viewModelScope.launch {
            try {
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
                        giroVeloce.value = "Dati non disponibili"
                    } else {
                        giroVeloce.value = pilota
                    }
                } else {
                    giroVeloce.value = "Errore nella richiesta"
                }
            } catch (e: Exception) {
                giroVeloce.value = "Errore: ${e.message}"
            }
        }

        return giroVeloce
    }
}