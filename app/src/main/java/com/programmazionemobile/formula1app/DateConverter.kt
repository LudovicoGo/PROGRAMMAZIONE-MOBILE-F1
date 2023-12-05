package com.programmazionemobile.formula1app

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date
import java.time.ZoneOffset
import java.time.format.DateTimeParseException
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

    fun convertDateYear(originalDate: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy", Locale.ITALIAN)

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
    fun oreRimanenti(dataString: String, orarioGaraString: String): String {
        // Formato della data e dell'orario desiderato
        val formatoData = SimpleDateFormat("yyyy-MM-dd HH:mm:ssX", Locale.getDefault())
        formatoData.timeZone = TimeZone.getTimeZone("UTC")

        try {
            // Converti la stringa della data e dell'orario in una data
            val dataSpecificata = formatoData.parse("$dataString $orarioGaraString")

            // Ottieni la data e l'orario correnti
            val dataCorrente = Date()

            // Calcola la differenza tra le due date in millisecondi
            val differenzaInMillisecondi = dataSpecificata.time - dataCorrente.time

            if (differenzaInMillisecondi >= 0) {
                // Calcola il numero di ore rimanenti (dividendo per 3,600,000 millisecondi all'ora)
                val oreRimanenti = (differenzaInMillisecondi % 86_400_000) / 3_600_000
                return if (oreRimanenti > 0) oreRimanenti.toString() else "00"
            } else {
                return "00" // La data e l'orario specificati sono nel passato
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return "00" // Gestisci qualsiasi eccezione restituendo un valore predefinito
        }
    }


    fun minutiRimanenti(dataString: String, orarioGaraString: String): String {
        // Formato della data e dell'orario desiderato
        val formatoData = SimpleDateFormat("yyyy-MM-dd HH:mm:ssX")
        formatoData.timeZone = TimeZone.getTimeZone("UTC")

        try {
            // Ottieni la data e l'orario correnti
            val dataCorrente = Date()

            // Converti la stringa della data in una data
            val dataSpecificata = formatoData.parse("$dataString $orarioGaraString")

            // Calcola la differenza tra le due date in millisecondi
            val differenzaInMillisecondi = dataSpecificata.time - dataCorrente.time


            // Calcola il numero di ore rimanenti (dividendo per 3,600,000 millisecondi all'ora)
            val minutiRimanenti = ((differenzaInMillisecondi % 86_400_000) % 3_600_000) / 60_000

            return if (minutiRimanenti > 0)
                minutiRimanenti.toString()
            else
                "00"
        } catch (e: Exception) {
            // Gestione dell'eccezione nel caso in cui la stringa della data non sia valida
            return "DN"
        }
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

    fun calcolaMinutiMancanti(dataDesiderata: String, orarioString: String): Long {
        val dataDesiderataString = "$dataDesiderata-$orarioString"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ssX")
        val dataDesiderata = LocalDateTime.parse(dataDesiderataString, formatter)
        val dataAttuale = LocalDateTime.now()

        return ChronoUnit.MINUTES.between(dataAttuale, dataDesiderata)
    }

    fun calcolaTempoMancanteGiorni(dataDaConfrontare: String, orarioDaConfrontare: String): String {
        if (dataDaConfrontare == "Dati non disponibili") {
            return "Data non disponibile"
        }

        val formatoData = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX")
        val now = LocalDateTime.now()

        return try {
            val dataConfronto = LocalDateTime.parse("$dataDaConfrontare $orarioDaConfrontare", formatoData)
            val differenza = ChronoUnit.DAYS.between(now, dataConfronto)
            if (differenza > 0)
                differenza.toString()
            else if (differenza.toInt() == 0)
                "00"
            else "00"
        } catch (e: DateTimeParseException) {
            e.printStackTrace()
            "Data non valida"
        }
    }


    fun convertDateMessages(date: Date): String {
        val sdf = SimpleDateFormat("HH:mm - dd/MM/yyyy", Locale.getDefault())
        return sdf.format(date)
    }

    fun isRaceDateAfterToday(raceDate: String): Boolean {
        val outputFormat = SimpleDateFormat("dd MMMM", Locale.ITALIAN)
        val currentDate = Date()

        try {
            val formattedRaceDate = outputFormat.parse(raceDate)
            return currentDate.before(formattedRaceDate)
        } catch (e: Exception) {
            // Gestisci eventuali eccezioni durante il parsing della data
            e.printStackTrace()
            return false
        }
    }
}