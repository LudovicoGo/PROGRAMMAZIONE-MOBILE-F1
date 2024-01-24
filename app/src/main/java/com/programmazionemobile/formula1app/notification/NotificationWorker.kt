package com.programmazionemobile.formula1app.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.programmazionemobile.formula1app.R

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

//    private val raceNotificationDatesSet = HashSet<String>()
    override fun doWork(): Result {
        val raceName = inputData.getString("raceName")
        val raceSeason = inputData.getString("raceSeason")

        val sharedPreferences = applicationContext.getSharedPreferences("preferenze_utente", Context.MODE_PRIVATE)
        val isNotificationEnabled = sharedPreferences.getBoolean("notification_enabled", false)

        if (isNotificationEnabled && raceName != null && raceSeason != null) {
            sendNotification(raceName, raceSeason)
        }
//        sendNotification()
        Log.d("Notificatio__nWorker", "Lavoro completato " + isNotificationEnabled)
        return Result.success()
    }

    private fun sendNotification(raceName: String, raceSeason: String) {

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setContentTitle("${raceName + ' ' + raceSeason}")
            .setContentText("Race will starts soon! Join the live chat now!")
            .setSmallIcon(R.drawable.img_2)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        Log.d("SENDOTIVICATION 1111", "in sendnotification")

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notificationManager.notify(NOTIFICATION_ID, notification)
        NOTIFICATION_ID++
    }

    companion object {
        private const val CHANNEL_ID = "chat_channel"
        private var NOTIFICATION_ID = 123
    }

}