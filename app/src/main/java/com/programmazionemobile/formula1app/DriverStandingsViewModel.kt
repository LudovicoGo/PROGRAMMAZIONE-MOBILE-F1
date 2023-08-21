package com.programmazionemobile.formula1app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStanding
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStandingsDataClass
import com.programmazionemobile.formula1app.data.driverStandingsData.StandingsLists
import com.programmazionemobile.formula1app.data.interfaceAPI.ErgastApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DriverStandingsViewModel : ViewModel() {
    private val BASE_URL = "https://ergast.com/api/f1/"
    private val TAG: String = "CHECK_RESPONSE"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ErgastApi::class.java)

    //    private val _driverStandings = MutableLiveData<DriverStandingsDataClass>()
    private val _driverStandings = MutableLiveData<List<DriverStanding>>()

    //    val driverStandings: LiveData<String>
//    val driverStandings: LiveData<DriverStandingsDataClass>
    val driverStandings: LiveData<List<DriverStanding>>
        get() = _driverStandings

    fun getAllDriverStandings() {
        viewModelScope.launch {
            try {
                val response = api.getDriverStandings()


                if (response.isSuccessful) {
                    val driverStandingsData = response.body()


                    if (driverStandingsData != null) {
                        val driverStandingsList = mutableListOf<DriverStanding>()
                        Log.d("FAIL", "NON NULLONON NULLONON NULLONON NULLONON NULLONON NULLO")
                        for (standingsList in driverStandingsData.mRData.standingsTable.standingsLists) {
                            Log.d("FAIL", "PRIMO FOR")

                            for (driverStanding in standingsList.driverStandings) {
                                driverStandingsList.add(driverStanding)
                                Log.d("FAIL", "SECONDO FOR")

                                Log.d("FAIL", "${driverStanding}")

                            }
                        }
                        _driverStandings.value = driverStandingsList
                        Log.d("driverStandingsListdriverStandingsListdriverStandingsListdriverStandingsList", "${driverStandingsList}")


                    }

                } else {
                    // Handle unsuccessful response
//                    _driverStandings.value = "API call failed with response code: ${response.code()}"
                    Log.d("FAIL", "API call failed with response code: ${response.code()}")
                }
            } catch (e: Exception) {
                // Handle exception
                Log.d("FAIL EXCEPTION", e.toString())
            }
        }
    }


}