package com.programmazionemobile.formula1app.model

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.interfaceAPI.ErgastApi
import kotlinx.coroutines.launch
import org.json.JSONObject
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

    private var seasons = "0"
    private val _driverData = MutableLiveData<Map<String, String>>()
    val driverData: LiveData<Map<String, String>>
        get() = _driverData


    fun getDriverSeasons(driverID: String): String {
        var total = "0"
        viewModelScope.launch {
            try {
                val response = api.getDriverSeasons(driverID)
                if (response.isSuccessful) {
                    val driverDataJson = response.body()
//                    Log.d(TAG, response.toString())

                    if (driverDataJson != null) {

                        total = driverDataJson.mRData.total
                        Log.d(TAG, "stagioni "+ total)
                    } else {
                        total = "0"
                    }

                }
                seasons = total
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }
        return total
    }

    private fun getCareerFLaps(driverID: String): String {
        var fLaps = 0

        return fLaps.toString()
    }

    private fun getCareerTitles(driverID: String): String {
        var titles = 0

        return titles.toString()
    }

    fun getDriverStats(driverID: String) {
        var careerRaces = ""
        var careerWins = 0
        var seasonWins = 0
        var careerPodiums = 0
        var seasonPodiums = 0
        var careerPoints = 0.0
        var careerFLap = 0
        var careerPoles = 0
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        var seasons = mutableListOf<String>()

        viewModelScope.launch {
            try {
                val response = api.getDriverResults(driverID)
                var responseObj = response.body()
                if (response.isSuccessful) {
                    if (responseObj != null) {
                        Log.d(TAG, response.body().toString())
                        careerRaces = responseObj.mRData.total

                        for (race in responseObj.mRData.raceTable.races) {
                            for (result in race.results) {
                                careerPoints += result.points.toFloat()
                                if (result.position == "1") {
                                    careerWins++
                                    careerPodiums++
                                    if(!seasons.contains(race.season)){
                                        seasons.add(race.season)
                                        }
                                    if (race.season == currentYear.toString()){
                                        seasonWins++
                                        seasonPodiums++
                                    }
                                } else if (result.position == "2" || result.position == "3") {
                                    careerPodiums++
                                    if (race.season == currentYear.toString()){
                                        seasonPodiums++
                                    }
                                }

                                /*   if (result.fastestLap.rank == "1" && result.fastestLap.rank != null){
                                       careerFLap++
                                   }*/
                                if (result.grid == "1") {
                                    careerPoles++
                                }
                            }
                        }


                    }


                    Log.d(TAG, "gare ${careerRaces}")
                    Log.d(TAG, "punti ${careerPoints}")
                    Log.d(TAG, "vittorie ${careerWins}")
                    Log.d(TAG, "podi ${careerPodiums}")
                    Log.d(TAG, "giri veloci ${careerFLap}")
                    Log.d(TAG, "poles ${careerPoles}")



                    Log.d(TAG, "seasons ${seasons.size}")


                    var data = mutableMapOf(
                        "careerRaces" to "${careerRaces}",
                        "careerWins" to "${careerWins}",
                        "seasonWins" to "${seasonWins}",
                        "careerPodiums" to "${careerPodiums}",
                        "seasonPodiums" to "${seasonPodiums}",
                        "careerPoints" to "${careerPoints}",
                        "careerPoles" to "${careerPoles}",
                        "careerSeasons" to seasons.size.toString(),
                        "careerTitles" to getCareerTitles(driverID),
                        "careerFLap" to getCareerFLaps(driverID)
                    )

                    _driverData.value = data


                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
        }


    }
}
