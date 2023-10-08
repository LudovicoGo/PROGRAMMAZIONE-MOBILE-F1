package com.programmazionemobile.formula1app.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.interfaceAPI.ErgastApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DriverProfileViewModel : ViewModel() {
    private val BASE_URL = "https://ergast.com/api/f1/"
    private val TAG: String = "CHECK_RESPONSE"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ErgastApi::class.java)


    private var data: MutableMap<String, String> = mutableMapOf()
//    private var driverPoints = 0.0


    private val _driverData = MutableLiveData<Map<String, String>>()
    val driverData: LiveData<Map<String, String>>
        get() = _driverData

    private val _driverStandingsData = MutableLiveData<Map<String, String>>()
    val driverStandingsDataPoints: LiveData<Map<String, String>>
        get() = _driverStandingsData

    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean>
        get() = _isInternetConnected

    init{
        _isInternetConnected.value = true
    }

/*
    private fun getCareerTitles(driverID: String): String {
        var titles = 0

        return titles.toString()
    }*/

    fun getDriverPointsTitles(driverID: String) {//per prendere il totale dei punti in tutte le stagioni passate
        viewModelScope.launch {
            var points = 0.0
            var titles = 0
            try {
                val response = api.getSingleDriverStandings(driverID)
                if (response.isSuccessful) {
                    val driverStandingsData = response.body()

                    if (driverStandingsData != null) {
                        for (standings in driverStandingsData.mRData.standingsTable.standingsLists) {
                            for (driverStanding in standings.driverStandings) {
                                points += driverStanding.points.toFloat()
                                if(driverStanding.position == "1"){
                                    titles++
                                }
                            }
                        }
                    }
                    data = mutableMapOf(
                        "careerPoints" to points.toString(),
                        "careerTitles" to titles.toString()
                    )
                    _driverStandingsData.value = data
                }
                _isInternetConnected.value = true

            } catch (e: Exception) {
                Log.d(TAG, e.toString())
                _isInternetConnected.value = false
//                if(_isInternetConnected.value == true)
            }
        }
    }


    fun getDriverStats(driverID: String, selectedYearSpinner: String) {
        var careerRaces = ""
        var careerWins = 0
        var seasonWins = 0
        var careerPodiums = 0
        var seasonPodiums = 0
        var careerFLap = 0
        var careerPoles = 0
//        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val currentYear = selectedYearSpinner
        var seasons = mutableListOf<String>()


        viewModelScope.launch {
            try {
                val response = api.getDriverResults(driverID)
                var responseObj = response.body()
                if (response.isSuccessful) {
                    if (responseObj != null) {
//                        Log.d(TAG, response.body().toString())
                        careerRaces = responseObj.mRData.total

                        for (race in responseObj.mRData.raceTable.races) {
                            if (!seasons.contains(race.season)) {
                                seasons.add(race.season)
                            }
                            for (result in race.results) {
                                if (result.position == "1") {
                                    careerWins++
                                    careerPodiums++
                                    if (race.season == currentYear.toString()) {
                                        seasonWins++
                                        seasonPodiums++
                                    }
                                } else if (result.position == "2" || result.position == "3") {
                                    careerPodiums++
                                    if (race.season == currentYear.toString()) {
                                        seasonPodiums++
                                    }
                                }

                                if (result.fastestLap != null) {
                                    if (result.fastestLap.rank == "1") {
                                        careerFLap++
                                    }
                                }
                                if (result.grid == "1") {
                                    careerPoles++
                                }
                            }
                        }


                    }


//                    Log.d(TAG, "gare ${careerRaces}")
//                    Log.d(TAG, "vittorie ${careerWins}")
//                    Log.d(TAG, "podi ${careerPodiums}")
//                    Log.d(TAG, "giri veloci ${careerFLap}")
//                    Log.d(TAG, "poles ${careerPoles}")
//                    Log.d(TAG, "seasons ${seasons.size}")

                    var data = mutableMapOf(
                        "careerRaces" to "${careerRaces}",
                        "careerWins" to "${careerWins}",
                        "seasonWins" to "${seasonWins}",
                        "careerPodiums" to "${careerPodiums}",
                        "seasonPodiums" to "${seasonPodiums}",
                        "careerPoles" to "${careerPoles}",
                        "careerSeasons" to seasons.size.toString(),
//                        "careerTitles" to getCareerTitles(driverID),
                        "careerFLap" to careerFLap.toString()
                    )
                    _driverData.value = data

                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }


    }
    fun setInternetConnectionStatus(isConnected: Boolean) {
        _isInternetConnected.value = isConnected
    }

}
