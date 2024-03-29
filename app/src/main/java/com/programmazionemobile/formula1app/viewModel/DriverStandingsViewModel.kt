package com.programmazionemobile.formula1app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStanding
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

    //per progressbar
    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean>
        get() = _loadingState



    private val _driverStandings = MutableLiveData<List<DriverStanding>>()

    val driverStandings: LiveData<List<DriverStanding>>
        get() = _driverStandings

    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean>
        get() = _isInternetConnected


    init {
        _isInternetConnected.value = true
        _loadingState.value = false //imposto a falso lo stato di caricamento all'inizio
    }


    fun getAllDriverStandings(year: String) {
        viewModelScope.launch {
            try {
                //quando devo eseguire la chiamata imposto a true lo stato del caricamento
                _loadingState.value = true

                val response = api.getDriverStandings(year)
//                val response = api.getDriverStandings("2024")

                _isInternetConnected.value = true


                if (response.isSuccessful) {
                    val driverStandingsData = response.body()
//                    Log.d("FAIL", "${driverStandingsData.toString()}")


                    if (driverStandingsData != null) {
                        val driverStandingsList = mutableListOf<DriverStanding>()
                        for (standingsList in driverStandingsData.mRData.standingsTable.standingsLists) {

                            for (driverStanding in standingsList.driverStandings) {
                                driverStandingsList.add(driverStanding)
                            }
                        }
                        _driverStandings.value = driverStandingsList


                    }

                } else {
                    // Handle unsuccessful response
//                    _driverStandings.value = "API call failed with response code: ${response.code()}"
//                    Log.d("FAIL", "API call failed with response code: ${response.code()}")
                }
            } catch (e: Exception) {
                // Handle exception
                Log.d("FAIL, EXCEPTION:", e.toString())
                _isInternetConnected.value = false
            } finally {//dopo aver terminato la chiamata alle api imposto lo stato di caricamento a falso
            _loadingState.value = false
        }
        }
    }


}