package com.programmazionemobile.formula1app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.constructorStandingsData.ConstructorStanding
import com.programmazionemobile.formula1app.data.interfaceAPI.ErgastApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConstructorStandingsViewModel : ViewModel() {
    private val BASE_URL = "https://ergast.com/api/f1/"
    private val TAG: String = "CHECK_RESPONSE"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ErgastApi::class.java)

    //per progressbar
//    private val _loadingState = MutableLiveData<Boolean>()
//    val loadingState: LiveData<Boolean>
//        get() = _loadingState


    private val _constructorStandings = MutableLiveData<List<ConstructorStanding>>()

    val constructorStandings: LiveData<List<ConstructorStanding>>
        get() = _constructorStandings

    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean>
        get() = _isInternetConnected

    init{
        _isInternetConnected.value = true
    }


    fun getAllConstructorStandings(year: String) {
        viewModelScope.launch {
            try {
//                _loadingState.value = true

                val response = api.getConstructorStandings(year)

                _isInternetConnected.value = true
                val constructorStandingsList = mutableListOf<ConstructorStanding>()


                if (response.isSuccessful) {
                    val constructorStandingsData = response.body()
//                    Log.d("COSTRUTTORI", "${constructorStandingsData.toString()}")

                    var constructorStandingString = ""

                    if (constructorStandingsData != null) {
                        val constructorStandingsList = mutableListOf<ConstructorStanding>()
                        for (standingsList in constructorStandingsData.mRData.standingsTable.standingsLists) {

                            for (driverStanding in standingsList.constructorStandings) {
                                constructorStandingsList.add(driverStanding)


                            }
                        }
                        _constructorStandings.value = constructorStandingsList
//                    Log.d("COSTRUTTORI", "${constructorStandingString.toString()}")*/


                    }

                } else {
                   //                    Log.d("FAIL", "API call failed with response code: ${response.code()}")
                }


            } catch (e: Exception) {
                // Handle exception
                Log.d("FAIL, EXCEPTION:", e.toString())
                _isInternetConnected.value = false
            } /*finally {//dopo aver terminato la chiamata alle api imposto lo stato di caricamento a falso
                _loadingState.value = false
            }*/


        }
    }
}