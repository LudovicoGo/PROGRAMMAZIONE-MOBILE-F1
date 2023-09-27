package com.programmazionemobile.formula1app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.Calendar
import android.icu.util.TimeUnit
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.*
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import coil.load
import coil.transform.RoundedCornersTransformation
import com.programmazionemobile.formula1app.DateConverter
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.data.calendarData.Race
import com.programmazionemobile.formula1app.notification.NotificationWorker
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.time.DurationUnit



import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.work.*
import com.google.gson.Gson

class CalendarAdapter (val data: MutableList<Race>, val context: Context): Adapter<ViewHolder>(){

    companion object{
        private const val HEADER_NUMBER = 1
        private const val TYPE_ITEM_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    class RaceListViewHolder(val row: View) : ViewHolder(row) {
        val dataGara = row.findViewById<TextView>(R.id.dataGara)
        val nomeGara = row.findViewById<TextView>(R.id.nomeGara)
        val descGara = row.findViewById<TextView>(R.id.descrizioneGara)
        val flagGara = row.findViewById<ImageView>(R.id.flagGara)
    }

    class HeaderViewHolder(val row: View): ViewHolder(row) {
        val dataProssimaGara = row.findViewById<TextView>(R.id.dataProssimaGara)
        val nomeProssimaGara = row.findViewById<TextView>(R.id.nomeProssimaGara)
        val descProssimaGara = row.findViewById<TextView>(R.id.descrizioneProssimaGara)
        val flagProssimaGara = row.findViewById<ImageView>(R.id.flagProssimaGara)
        val prossimoEventoText = row.findViewById<TextView>(R.id.proxEv)
        val prossimoEventoCard = row.findViewById<ImageView>(R.id.proxEvCard)
        val calendarHeader = row.findViewById<View>(R.id.calendar_header)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return when(viewType){
            TYPE_ITEM_HEADER -> {
                //HEADER
                HeaderViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.header_race_recycler_view, parent, false)
                )
            }
            else -> {
                //ITEM
                RaceListViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.race_recycler_view, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, round: Int) {

        when(holder) {
            is HeaderViewHolder -> {
                if (DateConverter.convertDateYear(data.get(round).date) == "2023"){
                    holder.dataProssimaGara.text = DateConverter.convertDate(nextRace(data)?.date!!)
                    holder.nomeProssimaGara.text = nextRace(data)?.raceName
                    holder.descProssimaGara.text = nextRace(data)?.circuit?.circuitName
                    val countryName = nextRace(data)?.circuit?.location?.country

                    val countryCode = getCountryCode(countryName!!)

                    val bundle = Bundle()
                    bundle.putString("raceName", nextRace(data)?.raceName)
                    bundle.putString("circuitName", nextRace(data)?.circuit?.circuitName)
                    bundle.putString("circuitID", nextRace(data)?.circuit?.circuitId)
                    bundle.putString("raceDate", nextRace(data)?.date!!)
                    bundle.putString("firstDate", nextRace(data)?.firstPractice?.date!!)
                    bundle.putString("qualiDate", nextRace(data)?.qualifying?.date!!)
                    bundle.putString("raceHour", nextRace(data)?.time)
                    bundle.putString("qualiHour", nextRace(data)?.qualifying?.time!!)
                    bundle.putString("calendarRound", nextRace(data)?.round)


                    holder.flagProssimaGara.load("https://flagpedia.net/data/flags/w1160/$countryCode.webp")
                    {
                        transformations(RoundedCornersTransformation
                            (75f, 30f, 0f, 30f))
                    }

                    holder.row.findViewById<View>(R.id.prossimoEvento).setOnClickListener {
                        it.findNavController().navigate(R.id.action_calendarFragment_to_raceFragment2, bundle)
                    }
                } else{
                    holder.dataProssimaGara.visibility = GONE
                    holder.nomeProssimaGara.visibility = GONE
                    holder.descProssimaGara.visibility = GONE
                    holder.flagProssimaGara.visibility = GONE
                    holder.prossimoEventoText.visibility = GONE
                    holder.prossimoEventoCard.visibility = GONE

                    holder.calendarHeader.layoutParams.height = 240

                }
            }

            is RaceListViewHolder -> {
                holder.dataGara.text = DateConverter.convertDate(data.get(round - 1).date)
                holder.nomeGara.text = data.get(round - 1).raceName
                holder.descGara.text = data.get(round - 1).circuit.circuitName
                val countryName = data.get(round - 1).circuit.location.country

                val countryCode = getCountryCode(countryName)

                val bundle = Bundle()
                bundle.putString("raceName", data.get(round - 1).raceName)
                bundle.putString("circuitName", data.get(round - 1).circuit.circuitName)
                bundle.putString("circuitID", data.get(round - 1).circuit.circuitId)
                bundle.putString("raceDate", data.get(round - 1).date)
                bundle.putString("calendarRound", data.get(round-1).round)


                if (DateConverter.convertDateYear(data.get(round - 1).date).toInt() > 2021){
                    bundle.putString("qualiDate", data.get(round -1).qualifying.date)
                    bundle.putString("qualiHour", data.get(round - 1).qualifying.time)
                    bundle.putString("firstDate", data.get(round - 1).firstPractice.date)
                } else{
                    bundle.putString("qualiHour", "Dati non disponibili")
                    bundle.putString("qualiDate", "Dati non disponibili")
                }

                if (DateConverter.convertDateYear(data.get(round - 1).date).toInt() > 2004)
                    bundle.putString("raceHour", data.get(round - 1).time)
                else
                    bundle.putString("raceHour", "Dati non disponibili")


                holder.flagGara.load("https://flagpedia.net/data/flags/w1160/$countryCode.webp")
                {
                    transformations(RoundedCornersTransformation
                        (75f, 30f, 0f, 30f))
                }

                holder.itemView.setOnClickListener {
                    it.findNavController().navigate(R.id.action_calendarFragment_to_raceFragment2, bundle)
                }

            }
        }
    }

    override fun getItemViewType(round: Int): Int {
        if (round < HEADER_NUMBER) {
            return TYPE_ITEM_HEADER
        }
        return TYPE_ITEM
    }

    override fun getItemCount(): Int {
        // conta gli elementi per la recyclerview
        return data.size + HEADER_NUMBER
    }

    fun getCountryCode(countryName: String): String? {
        val countryNameToCodeMap = mapOf(
            "Australia" to "au",
            "Austria" to "at",
            "Azerbaijan" to "az",
            "Bahrain" to "bh",
            "Belgium" to "be",
            "Brazil" to "br",
            "Canada" to "ca",
            "China" to "cn",
            "France" to "fr",
            "Germany" to "de",
            "Hungary" to "hu",
            "India" to "in",
            "Italy" to "it",
            "Japan" to "jp",
            "Malaysia" to "my",
            "Mexico" to "mx",
            "Monaco" to "mc",
            "Netherlands" to "nl",
            "Portugal" to "pt",
            "Russia" to "ru",
            "Singapore" to "sg",
            "South Africa" to "za",
            "Saudi Arabia" to "sa",
            "Spain" to "es",
            "Sweden" to "se",
            "Switzerland" to "ch",
            "Turkey" to "tr",
            "UAE" to "ae",
            "United Arab Emirates" to "ae",
            "United Kingdom" to "gb",
            "UK" to "gb",
            "United States" to "us",
            "USA" to "us",
            "Qatar" to "qa",
            "Korea" to "kr",
            "Argentina" to "ar"
        )
        return countryNameToCodeMap[countryName]
    }

    @SuppressLint("SimpleDateFormat")
    fun nextRace(gare: List<Race>): Race? {
        // Ottieni la data corrente
        val dataCorrente = LocalDate.now()

        // Inizializza la gara prossima
        var garaProssima: Race? = null
        var dataProssima: LocalDate? = null

        // Formato per il parsing delle date
        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        // Itera attraverso tutte le gare nella collezione
        for (gara in gare) {
            val dataGara = LocalDate.parse(gara.date, dateFormat)

            // Se la data della gara è nel futuro e più vicina alla data corrente rispetto
            // alla gara prossima attualmente impostata, aggiornala come gara prossima
            if (dataGara.isAfter(dataCorrente) && (dataProssima == null || dataGara.isBefore(dataProssima))) {
                garaProssima = gara
                dataProssima = dataGara
            }

            // Se la data della gara è uguale alla data corrente, restituiscila immediatamente
            if (dataGara == dataCorrente) {
                garaProssima = gara
                break
            }
        }
        return garaProssima
    }

    fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)
    }

}