package com.programmazionemobile.formula1app.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.calendarData.Circuit
import com.programmazionemobile.formula1app.data.calendarData.Race
import com.programmazionemobile.formula1app.data.interfaceAPI.Service
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CircuitInfoViewModel: ViewModel() {
    private val BASE_URL = "https://ergast.com/api/f1/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Service::class.java)

    private val _circuit = MutableLiveData<Map<String, String>>()

    val circuit: LiveData<Map<String, String>>
        get() = _circuit

    fun getCircuitName(circuitId: String): String{
        var circuitName = "cialve"
        viewModelScope.launch {
            val response = api.infoCircuit(circuitId)

            if (response.isSuccessful){
                val info = response.body()

                if (info != null){
                    circuitName = info.circuitName
                }
            }
        }
        return circuitName
    }

}