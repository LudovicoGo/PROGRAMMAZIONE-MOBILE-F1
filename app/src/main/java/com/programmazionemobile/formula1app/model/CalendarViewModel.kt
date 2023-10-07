package com.programmazionemobile.formula1app.model

import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.calendarData.Race
import com.programmazionemobile.formula1app.data.interfaceAPI.Service
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll
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

    private val _isInternetConnected = MutableLiveData<Boolean>()
    val isInternetConnected: LiveData<Boolean>
        get() = _isInternetConnected

    init{
        _isInternetConnected.value = true
    }

    /*

        fun checkInternetConnection() {
            _isInternetConnected.value = isInternetConnected()
        }

        private fun isInternetConnected(): Boolean {
            val connectivityManager =
                ContextCompat.getSystemService(context, ConnectivityManager::class.java)

            val activeNetworkInfo = connectivityManager?.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
            // Implementa la logica per verificare la connessione Internet, come descritto in precedenza.
            // Restituisce true se la connessione è disponibile, altrimenti false.
        }
    */

    fun getCalendar(year: String) {
        viewModelScope.launch {

            try{
                val response = api.currentCalendar(year)

                _isInternetConnected.value = true
                if (response.isSuccessful) {
                    val calendarData = response.body()

                    if (calendarData != null) {
                        val calendarList = mutableListOf<Race>()

                        for (list in calendarData.mRData.raceTable.races) {
                            calendarList.add(list)
                        }
                        _calendar.value = calendarList

                    } else {
                        // Handle unsuccessful response
//                    _driverStandings.value = "API call failed with response code: ${response.code()}"
                        Log.d("FAIL", "API call failed with response code: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Log.d("FAIL, EXCEPTION:", e.toString())

//                if(calendar.value.isNullOrEmpty() || _isInternetConnected.value == false)
                    _isInternetConnected.value = false
//                notifyAll()

            }
        }
    }
}