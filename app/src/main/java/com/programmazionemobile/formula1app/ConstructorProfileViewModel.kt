package com.programmazionemobile.formula1app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.constructorDriversData.Driver
import com.programmazionemobile.formula1app.data.constructorResultsData.Race
import com.programmazionemobile.formula1app.data.interfaceAPI.ErgastApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConstructorProfileViewModel : ViewModel() {
    private val BASE_URL = "https://ergast.com/api/f1/"
    private val TAG: String = "CHECK_RESPONSE"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ErgastApi::class.java)


    private var data: MutableMap<String, String> = mutableMapOf()
//    private var constructorDrivers: MutableMap<String, String> = mutableMapOf()


    private val _constructorDrivers = MutableLiveData<List<Driver>>()
    val constructorDrivers: LiveData<List<Driver>>
        get() = _constructorDrivers


    private val _constructorData = MutableLiveData<Map<String, String>>()
    val constructorData: LiveData<Map<String, String>>
        get() = _constructorData

    private val _constructorTitles = MutableLiveData<String>()
    val constructorTitles: LiveData<String>
        get() = _constructorTitles





    fun getConstructorTitles(constructorID: String) {//per prendere il numero di titoli costruttori vinti
        viewModelScope.launch {
            var titles = 0
            try {
                val response = api.getConstructorChampions()

                if (response.isSuccessful) {

                    val constructorStandingsData = response.body()

                    if (constructorStandingsData != null) {
                        for (standings in constructorStandingsData.mRData.standingsTable.standingsLists) {
                            for (constructor in standings.constructorStandings) {
                                if(constructor.constructor.constructorId == constructorID && constructor.position == "1")
                                    titles++
                            }
                        }
                    }
                    _constructorTitles.value = titles.toString()
                }

            } catch (e: Exception) {
                Log.d(TAG, e.toString())
            }

        }
    }




    fun getConstructorDrivers(constructorID: String, selectedYearSpinner: String) {

        viewModelScope.launch {
            try {
                val response = api.getConstructorDrivers(constructorID, selectedYearSpinner)
                var responseObj = response.body()?.mRData?.driverTable?.drivers
                if (response.isSuccessful) {
                    if (responseObj != null) {
                        var drivers = mutableListOf<Driver>()
                        for (driver in responseObj) {
                            drivers.add(driver)
//                            Log.d(TAG, driver.toString())

                        }
                        _constructorDrivers.value = drivers
//                        Log.d(TAG, responseObj[0].toString() + " " + responseObj[1].toString())
                    }

                }

            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }

        }
    }

    fun getConstructorStats(constructorID: String, selectedYearSpinner: String, offset: String) {
        var historyRaces = 0
        var historyWins = 0
        var seasonWins = 0
        var historyPodiums = 0
        var seasonPodiums = 0
        var historyFLap = 0
        var historyPoles = 0
//        val currentYear = selectedYearSpinner
        var seasons = mutableListOf<String>()
        var offsetCounter = 0
        var totalStarts = 0
//        val races: List<Race>

        val races: MutableList<Race> = mutableListOf()

        viewModelScope.launch {
            try {
                val response = api.getConstructorResults(constructorID, offset =  offset)
                var responseObj = response.body()
                if (response.isSuccessful) {
                    if (responseObj != null) {
                        totalStarts = responseObj.mRData.total.toInt()
//
                        races += responseObj.mRData.raceTable.races
                        totalStarts -= 1000

                        while (totalStarts > 0) {
                            offsetCounter++
//                            Log.d("OFFSET", "COUNTER ${offsetCounter}")
//                            Log.d("OFFSET", "totalStarts ${totalStarts}")

                                val offsetResponse = api.getConstructorResults(
                                    constructorID, offset = "${offsetCounter * 1000}"//1000
                                )
                                if (offsetResponse.isSuccessful) {
                                    if (offsetResponse.body() != null) {
                                        races += offsetResponse.body()!!.mRData.raceTable.races
                                    }
                                }
                            totalStarts -= 1000
                        }


                    }

                }

//                    Log.d(TAG, "gare ${historyRaces}")
//                    Log.d(TAG, "vittorie ${historyWins}")
//                    Log.d(TAG, "podi ${historyPodiums}")
//                    Log.d(TAG, "giri veloci ${historyFLap}")
//                    Log.d(TAG, "poles ${historyPoles}")
//                    Log.d(TAG, "seasons ${seasons.size}")






                for (race in races) {
                    historyRaces++

                    if (!seasons.contains(race.season)) {
                        seasons.add(race.season)
                    }

                    for (result in race.results) {
                        if (result.position == "1") {
                            historyWins++
                            historyPodiums++
                            if (race.season == selectedYearSpinner) {
                                seasonWins++
                                seasonPodiums++
                            }
                        } else if (result.position == "2" || result.position == "3") {
                            historyPodiums++
                            if (race.season == selectedYearSpinner) {
                                seasonPodiums++
                            }
                        }

                        if (result.fastestLap != null) {
                            if (result.fastestLap.rank == "1") {
                                historyFLap++
                            }
                        }
                        if (result.grid == "1") {
                            historyPoles++
                        }
                    }
                }


//                Log.d(TAG, "gare ${historyRaces}")
//                Log.d(TAG, "vittorie ${historyWins}")
//                Log.d(TAG, "podi ${historyPodiums}")
//                Log.d(TAG, "giri veloci ${historyFLap}")
//                Log.d(TAG, "poles ${historyPoles}")
//                Log.d(TAG, "seasons ${seasons.size}")

                var data = mutableMapOf(
                    "historyRaces" to "${historyRaces}",
                    "historyWins" to "${historyWins}",
                    "seasonWins" to "${seasonWins}",
                    "historyPodiums" to "${historyPodiums}",
                    "seasonPodiums" to "${seasonPodiums}",
                    "historyPoles" to "${historyPoles}",
                    "historySeasons" to seasons.size.toString(),
//                            "careerTitles" to historyTitles(driverID),
                    "historyFLap" to historyFLap.toString()
                )
                _constructorData.value = data



            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
    }
}