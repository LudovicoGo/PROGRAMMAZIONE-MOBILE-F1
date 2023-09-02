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
import java.time.Year


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

    fun getCalendar(year: String) {
        viewModelScope.launch {

            val response = api.currentCalendar(year)

            if (response.isSuccessful) {
                val calendarData = response.body()

                if (calendarData != null) {
                    val calendarList = mutableListOf<Race>()

                    for (list in calendarData.mRData.raceTable.races){
                        calendarList.add(list)
                    }
                    _calendar.value = calendarList

                } else {
                    // Handle unsuccessful response
//                    _driverStandings.value = "API call failed with response code: ${response.code()}"
                    Log.d("FAIL", "API call failed with response code: ${response.code()}")
                }
            }
        }
    }
}