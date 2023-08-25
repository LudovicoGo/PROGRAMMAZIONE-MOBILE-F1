package com.programmazionemobile.formula1app

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
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
    fun convertDateInfo(originalDate: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM y", Locale.ITALIAN)

            val date = inputFormat.parse(originalDate)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return originalDate // In caso di errore, restituisci la data originale
        }
    }

    fun convertDateGiorno(originalDate: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd", Locale.ITALIAN)

            val date = inputFormat.parse(originalDate)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return originalDate // In caso di errore, restituisci la data originale
        }
    }

    fun convertDateMese(originalDate: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMMM", Locale.ITALIAN)

            val date = inputFormat.parse(originalDate)
            return outputFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return originalDate // In caso di errore, restituisci la data originale
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun giorniRimanenti(dataString: String): String {
        // Formato della data desiderato
        val formatoData = SimpleDateFormat("yyyy-MM-dd")

        try {
            // Ottieni la data corrente
            val dataCorrente = Date()

            // Converti la stringa in una data
            val dataSpecificata = formatoData.parse(dataString)

            // Calcola la differenza tra le due date in millisecondi
            val differenzaInMillisecondi = dataSpecificata.time - dataCorrente.time

            // Calcola il numero di giorni rimanenti (dividendo per 86,400,000 millisecondi al giorno)
            val giorniRimanenti = (differenzaInMillisecondi / 86_400_000)+1

            return if (giorniRimanenti >= 0)
                giorniRimanenti.toString()
            else
                "00"
        } catch (e: Exception) {
            // Gestione dell'eccezione nel caso in cui la stringa della data non sia valida
            println("Errore: Data non valida.")
            return 30.toString()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun oreRimanenti(dataString: String, orarioGaraString: String): String {
        // Formato della data e dell'orario desiderato
        val formatoData = SimpleDateFormat("yyyy-MM-dd HH:mm:ssX")
        formatoData.timeZone = TimeZone.getTimeZone("UTC")

        try {
            // Ottieni la data e l'orario correnti
            val dataCorrente = Date()
            println("val dataCorrente = Date()")

            // Converti la stringa della data in una data
            val dataSpecificata = formatoData.parse("$dataString $orarioGaraString")
            println("val dataSpecificata = formatoData.parse(dataString orarioGaraStrin)")

            // Calcola la differenza tra le due date in millisecondi
            val differenzaInMillisecondi = dataSpecificata.time - dataCorrente.time
            println("val differenzaInMillisecondi = dataSpecificata.time - dataCorrente.time")


            // Calcola il numero di ore rimanenti (dividendo per 3,600,000 millisecondi all'ora)
            val oreRimanenti = (differenzaInMillisecondi % 86_400_000) / 3_600_000
            println("vval oreRimanenti = (differenzaInMillisecondi % 86400000) / 3600000")
            println(oreRimanenti)

            return if (oreRimanenti >= 0)
            {
                println(oreRimanenti)
                oreRimanenti.toString()
            }
            else
                "00"
        } catch (e: Exception) {
            // Gestione dell'eccezione nel caso in cui la stringa della data non sia valida
            println("Errore: Data non valida.")
            return "DN"
        }
    }

    fun minutiRimanenti(dataString: String, orarioGaraString: String): String {
        // Formato della data e dell'orario desiderato
        val formatoData = SimpleDateFormat("yyyy-MM-dd HH:mm:ssX")
        formatoData.timeZone = TimeZone.getTimeZone("UTC")

        try {
            // Ottieni la data e l'orario correnti
            val dataCorrente = Date()
            println(dataCorrente)

            // Converti la stringa della data in una data
            val dataSpecificata = formatoData.parse("$dataString $orarioGaraString")
            println("val dataSpecificata = formatoData.parse(dataString orarioGaraStrin)")

            // Calcola la differenza tra le due date in millisecondi
            val differenzaInMillisecondi = dataSpecificata.time - dataCorrente.time
            println("val differenzaInMillisecondi = dataSpecificata.time - dataCorrente.time")


            // Calcola il numero di ore rimanenti (dividendo per 3,600,000 millisecondi all'ora)
            val minutiRimanenti = ((differenzaInMillisecondi % 86_400_000) % 3_600_000) / 60_000
            println("vval oreRimanenti = (differenzaInMillisecondi % 86400000) / 3600000")
            println(minutiRimanenti)

            return if (minutiRimanenti >= 0)
            {
                println(minutiRimanenti)
                minutiRimanenti.toString()
            }
            else
                "00"
        } catch (e: Exception) {
            // Gestione dell'eccezione nel caso in cui la stringa della data non sia valida
            println("Errore: Data non valida.")
            return "DN"
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun convertUTCtoLocalTime(utcTime: String, dataString: String): String {
        val inputFormat = SimpleDateFormat("HH:mm:ssX")
        val outputFormat = SimpleDateFormat("HH : mm")
        val date = inputFormat.parse(utcTime)

        val timeZone = TimeZone.getTimeZone("Europe/Rome")
        outputFormat.timeZone = timeZone

        return outputFormat.format(date)
    }

    fun convertUTCtoLocalTime2(utcTime: String, dataString: String): String {
        // Parsing the input UTC time
        val utc = dataString+'T'+utcTime

        val utcInstant = Instant.parse(utc)

        // Creating a ZonedDateTime with the desired time zone (Europe/Rome)
        val romeZone = ZoneId.of("Europe/Rome")
        val localTime = ZonedDateTime.ofInstant(utcInstant, romeZone)

        // Formatting the output in the desired format
        val formatter = DateTimeFormatter.ofPattern("HH : mm")
        val formattedTime = localTime.format(formatter)

        return formattedTime
    }
}