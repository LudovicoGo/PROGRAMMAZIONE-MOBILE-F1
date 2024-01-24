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
    private val BASE_URL = "https://ergast.com/api/f1/"     //url a cui fare le richieste dei dati
    private val TAG: String = "CHECK_RESPONSE"

    private val api = Retrofit.Builder()    //creo oggetto retrofit
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())     //uso Gson per parsare il risultato xml in oggetti
        .build()
        .create(ErgastApi::class.java)      //collego l'oggetto retrofit alla classe service ErgastApi che contiene tutte le chiamate che posso fare per prendere i dati

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
        _isInternetConnected.value = true   //imposto internet presente prima di fare le chiamate al db cosi nascondo l'overlay all'apertura del fragment
    }


    fun getAllConstructorStandings(year: String) {  //metodo che prende la classifica del campionato
        viewModelScope.launch {
            try {
//                _loadingState.value = true

                val response = api.getConstructorStandings(year)

                _isInternetConnected.value = true
                val constructorStandingsList = mutableListOf<ConstructorStanding>()


                if (response.isSuccessful) {        //se la chiamata alle api va a buon fine e il risultato non è vuoto ne salvo il risultato
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