package com.programmazionemobile.formula1app

import android.icu.text.SimpleDateFormat
import java.util.Locale

object DateConverter {
    fun convertDate(originalDate: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM", Locale.ITALIAN)

            val date = inputFormat.parse(originalDate)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return originalDate // In caso di errore, restituisci la data originale
        }
    }
}