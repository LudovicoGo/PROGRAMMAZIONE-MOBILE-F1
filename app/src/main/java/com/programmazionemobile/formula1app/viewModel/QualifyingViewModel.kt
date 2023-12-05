package com.programmazionemobile.formula1app.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.interfaceAPI.ErgastApi
import com.programmazionemobile.formula1app.data.qualifyingData.QualifyingResult
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QualifyingViewModel : ViewModel() {
    private val BASE_URL = "https://ergast.com/api/f1/"
    private val TAG: String = "CHECK_RESPONSE"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ErgastApi::class.java)

    private val _qualifyingResults = MutableLiveData<List<QualifyingResult>>()

    val qualifyingResults: LiveData<List<QualifyingResult>>
        get() = _qualifyingResults

    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean>
        get() = _isInternetConnected

    init{
        _isInternetConnected.value = true
    }


    fun getQualifyingResults(year: String, round: String) {//per prendere il numero di titoli costruttori vinti
        viewModelScope.launch {
            try {

                val results: MutableList<QualifyingResult> = mutableListOf()
                val response = api.getQualifyingResults(year, round)
                _isInternetConnected.value = true
                println(response.body().toString() + year + round)

                if (response.isSuccessful) {

                    if(response.body() != null) {
                        for(race in response.body()!!.mRData.raceTable.races)
                        {
                            for(result in race.qualifyingResults){
                                Log.d(TAG, "${result.driver}, ${result.position}, ${result.q1}, ${result.q2}, ${result.q3}")
                                results += result
                            }
                        }
//                        Log.d(TAG, response.body().toString())
                    }

                    _qualifyingResults.value = results
                }

            } catch (e: Exception) {
                Log.d(TAG, e.toString())
                _isInternetConnected.value = false
            }

        }
    }

    fun setInternetConnectionStatus(isConnected: Boolean) {
        _isInternetConnected.value = isConnected
    }
}