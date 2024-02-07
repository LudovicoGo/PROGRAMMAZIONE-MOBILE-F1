package com.programmazionemobile.formula1app.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.programmazionemobile.formula1app.data.calendarData.Race
import com.programmazionemobile.formula1app.data.interfaceAPI.ErgastApi
import com.programmazionemobile.formula1app.data.interfaceAPI.Service
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.time.Duration.Companion.seconds

class NotificationApp : Application() {
    private val BASE_URL = "https://ergast.com/api/f1/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Service::class.java)

    private val races: MutableList<Race> = mutableListOf()

    private val raceNotificationDatesSet = HashSet<String>()


    override fun onCreate() {
        super.onCreate()
//        Log.d("INIZIO", "NOTIFICATION APP ON CREATE INIZIO")

//        clearSharedPreferences()

        val sharedPreferences = applicationContext.getSharedPreferences("preferenze_utente", Context.MODE_PRIVATE)
        val isNotificationEnabled = sharedPreferences.getBoolean("notification_enabled", false)

        createNotificationChannel()

        if (isNotificationEnabled){
            runBlocking {
                loadSharedPreferences()
            }
            runBlocking {
                loadData()
            }
        }
//        Log.d("TERMINATO", "NOTIFICATION APP ON CREATE TERMINATO")
    }

    fun generateNotifications() {
        raceNotificationDatesSet.clear()
        loadSharedPreferences()
        runBlocking {
                loadData()
        }
    }


    fun checkAndNotify(raceToCheck: Race) {
//        Log.d(raceToCheck.date, raceToCheck.date + "CHECKCKECKE")
        if (!raceNotificationDatesSet.contains(raceToCheck.date)) {  //se la data della gara non è presente nell'hashset allora creo la notifica
            if (raceToCheck.date < LocalDate.now().toString()) {
                return
            }
            scheduleNotificationForDate(raceToCheck)
            raceNotificationDatesSet.add(raceToCheck.date)       //aggiungo la data della gara all'hashset della classe
        }
        saveSharedPreferences()        //salvo le date aggiunte all'hashset della classe sulle shared references
    }

    private fun scheduleNotificationForDate(race: Race) {
        //vincoli che penso si possano togliere
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        //dati che passo insieme alla richiesta di work
        val inputRaceData = Data.Builder()
            .putString("raceName", race.raceName)
            .putString("raceSeason", race.season)
            .build()

        val raceDateTimeString = race.date + 'T' + race.time //creo una stringa nel formato "yyyy-MM-ddTHH:mm:ssZ"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
        //trasformo la stringa in un oggetto ZonedDateTime che tiene conto anche del fuso
        val raceDateTime = ZonedDateTime.parse(raceDateTimeString, formatter)
        //tolgo un giorno dalla data e ora della gara per avere la data e l'ora in cui mostrare la notifica
        val notificationDateTime = raceDateTime.minusDays(1)
        //differenza di tempo tra la data e l'ora corrente e la data della notifica programmata
        val currentTime = ZonedDateTime.now(ZoneId.of("UTC"))
        val delayInSeconds = Duration.between(currentTime, notificationDateTime).seconds


//        Log.d("scheduleNotificationForDate",  "SCHEDULO " + race.raceName + race.season + " " +  race.time + "per $delayInSeconds secondi cioè per il $notificationDateTime")

        //crea una richiesta di lavoro e gli assegno il tempo da far passare prima di eseguirlo
        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setConstraints(constraints)
             .setInitialDelay(delayInSeconds, java.util.concurrent.TimeUnit.SECONDS)
//            .setInitialDelay(5, java.util.concurrent.TimeUnit.SECONDS)
//            .setInitialDelay(25, java.util.concurrent.TimeUnit.SECONDS)
            .setInputData(inputRaceData)
            .build()
        //invio la richiesta di work
        WorkManager.getInstance(applicationContext).enqueue(workRequest)

    }


    private fun loadData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                var currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
                val response = api.currentCalendar(currentYear)

                if (response.isSuccessful) {
                    val responseData = response.body()
                    if (responseData != null) {
                        races.addAll(responseData.mRData.raceTable.races)
                        for (race in responseData.mRData.raceTable.races) {

//                            Log.d( "GARA DA RACES (LOAD LADA)", race.date + " " + race.raceName)
                            checkAndNotify(race)
                        }
                    } else {
                        Log.d("NOTIFICATION APP", "API call failed with response code: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                Log.d("NotificationApp Exception", e.toString())
            }
        }
    }

    private fun loadSharedPreferences(){
        val sharedPreferences = applicationContext.getSharedPreferences("preferenze_notifiche", Context.MODE_PRIVATE)
        raceNotificationDatesSet.addAll(sharedPreferences.getStringSet("date_pianificate", HashSet()) ?: HashSet())
    }

    private fun saveSharedPreferences(){
        val sharedPreferences = applicationContext.getSharedPreferences("preferenze_notifiche", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("date_pianificate", raceNotificationDatesSet)
        editor.apply()
    }

    fun clearSharedPreferences() {
        val sharedPreferences = applicationContext.getSharedPreferences("preferenze_notifiche", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    private fun createNotificationChannel() {
        //creo il canale di notifica
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                "chat_channel",
                "ChatNotification",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Used for the live chat notification"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }
}