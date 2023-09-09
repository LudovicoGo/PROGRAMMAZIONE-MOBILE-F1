package com.programmazionemobile.formula1app

import android.icu.util.Calendar
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.programmazionemobile.formula1app.data.constructorStandingsData.ConstructorStanding
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStanding
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStandingsDataClass
import com.programmazionemobile.formula1app.data.driverStandingsData.StandingsLists
import com.programmazionemobile.formula1app.data.interfaceAPI.ErgastApi
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Year

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


//    private val _constructorStandingsString = MutableLiveData<String>()

//    val constructorStandingsString: LiveData<String>
//        get() = _constructorStandingsString


    //imposto a falso lo stato di caricamento all'inizio
    /*  init {
          _loadingState.value = false
      }*/


    fun getAllConstructorStandings(year: String) {
        viewModelScope.launch {
            try {
//                _loadingState.value = true

                val response = api.getConstructorStandings(year)
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
                    /*
                    if (constructorStandingsData != null) {

                        for (constructor in constructorStandingsData.mRData.standingsTable.standingsLists[0].constructorStandings) {
                            constructorStandingsList.add(constructor)


//                            constructorStandingString =
//                                constructorStandingString + "cosotruttore: " + constructor.constructor + "\n" + "punti: " +
//                                        constructor.points + "\n" + "posizione: " + constructor.position + "\n" + "vittorie: " + constructor.wins + "\n\n\n"

                        }
                    }
                    _constructorStandings.value = constructorStandingsList
//                    _constructorStandingsString.value = constructorStandingString
//                    Log.d("COSTRUTTORI", "${constructorStandingString.toString()}")*/

                } else {
                    // Handle unsuccessful response
//                    _driverStandings.value = "API call failed with response code: ${response.code()}"
//                    Log.d("FAIL", "API call failed with response code: ${response.code()}")
                }


            } catch (e: Exception) {
                // Handle exception
                Log.d("FAIL, EXCEPTION:", e.toString())
            } /*finally {//dopo aver terminato la chiamata alle api imposto lo stato di caricamento a falso
                _loadingState.value = false
            }*/


        }
    }
}