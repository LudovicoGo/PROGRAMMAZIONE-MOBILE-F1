package com.programmazionemobile.formula1app.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.calendarData.Race
import com.programmazionemobile.formula1app.data.interfaceAPI.Service
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CalendarViewModel: ViewModel() {
    private val BASE_URL = "https://ergast.com/api/f1/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Service::class.java)

    private val _calendar = MutableLiveData<List<Race>>()

    val calendar: LiveData<List<Race>>
        get() = _calendar

    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean>
        get() = _isInternetConnected

    init{
        _isInternetConnected.value = true
    }

    fun getCalendar(year: String) {
        viewModelScope.launch {

            try{
                val response = api.currentCalendar(year)

                _isInternetConnected.value = true
                if (response.isSuccessful) {
                    Log.d("1FAIL", "API call failed with response code: ${response.code()}")

                    val calendarData = response.body()

                    if (calendarData != null) {
                        val calendarList = mutableListOf<Race>()

                        for (list in calendarData.mRData.raceTable.races) {
                            calendarList.add(list)
                        }
                        _calendar.value = calendarList

                    } else {
                        // Handle unsuccessful response
                        Log.d("FAIL", "API call failed with response code: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Log.d("FAIL, EXCEPTION:", e.toString())
                _isInternetConnected.value = false
            }
        }
    }
}