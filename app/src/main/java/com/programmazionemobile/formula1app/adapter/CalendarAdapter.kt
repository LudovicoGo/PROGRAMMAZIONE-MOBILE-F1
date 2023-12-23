package com.programmazionemobile.formula1app.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView.*
import coil.load
import coil.transform.RoundedCornersTransformation
import com.programmazionemobile.formula1app.DateConverter
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.data.calendarData.Race
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.work.*
import java.text.SimpleDateFormat


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
                if (DateConverter.convertDateYear(data.get(round).date) == "2024"){

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
                    bundle.putString("calendarRound", nextRace(data)?.round)

                    if (DateConverter.convertDateYear(data.get(round).date).toInt() in 2022..Calendar.getInstance().get(Calendar.YEAR)){
                        bundle.putString("qualiDate", nextRace(data)?.qualifying?.date)
                        bundle.putString("qualiHour", nextRace(data)?.qualifying?.time)
                        bundle.putString("firstDate", nextRace(data)?.firstPractice?.date)
                        bundle.putString("raceHour", nextRace(data)?.time)
                    } else{
                        bundle.putString("qualiDate", "Dati non disponibili")
                        bundle.putString("qualiHour", "Dati non disponibili")
                        bundle.putString("firstDate", "Dati non disponibili")
                        bundle.putString("raceHour", "Dati non disponibili")
                    }

                    holder.flagProssimaGara.load("https://flagpedia.net/data/flags/w1160/$countryCode.webp") {
                        transformations(RoundedCornersTransformation(75f, 30f, 0f, 30f))
                    }

                    holder.row.findViewById<View>(R.id.prossimoEvento).setOnClickListener {
                        it.findNavController().navigate(R.id.action_calendarFragment_to_raceFragment2, bundle)
                    }
                } else {
                    holder.dataProssimaGara.visibility = GONE
                    holder.nomeProssimaGara.visibility = GONE
                    holder.descProssimaGara.visibility = GONE
                    holder.flagProssimaGara.visibility = GONE
                    holder.prossimoEventoText.visibility = GONE
                    holder.prossimoEventoCard.visibility = GONE

                    holder.calendarHeader.layoutParams.height = 235
                }
            }


            is RaceListViewHolder -> {

                holder.dataGara.text = DateConverter.convertDate(data.get(round - HEADER_NUMBER).date)
                holder.nomeGara.text = data.get(round - HEADER_NUMBER).raceName
                holder.descGara.text = data.get(round - HEADER_NUMBER).circuit.circuitName
                val countryName = data.get(round - HEADER_NUMBER).circuit.location.country

                val countryCode = getCountryCode(countryName)

                val bundle = Bundle()
                bundle.putString("raceName", data.get(round - HEADER_NUMBER).raceName)
                bundle.putString("circuitName", data.get(round - HEADER_NUMBER).circuit.circuitName)
                bundle.putString("circuitID", data.get(round - HEADER_NUMBER).circuit.circuitId)
                bundle.putString("raceDate", data.get(round - HEADER_NUMBER).date)
                bundle.putString("calendarRound", data.get(round - HEADER_NUMBER).round)

                val primaGara = SimpleDateFormat("yyyy-MM-dd").parse(data[0].date)

                val dataCorrente = Calendar.getInstance().time.time

                if (dataCorrente >= primaGara.time
                    && DateConverter.convertDateYear(data.get(round - HEADER_NUMBER).date).toInt() > 2021) {
                    bundle.putString("qualiDate", data[round - HEADER_NUMBER].qualifying.date)
                    bundle.putString("qualiHour", data[round - HEADER_NUMBER].qualifying.time)
                    bundle.putString("firstDate", data[round - HEADER_NUMBER].firstPractice.date)
                } else {
                    bundle.putString("firstDate", "Dati non disponibili")
                    bundle.putString("qualiHour", "Dati non disponibili")
                    bundle.putString("qualiDate", "Dati non disponibili")
                }


                if (DateConverter.convertDateYear(data.get(round - HEADER_NUMBER).date).toInt() in 2004..Calendar.getInstance().get(Calendar.YEAR))
                    bundle.putString("raceHour", data.get(round - HEADER_NUMBER).time)
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
            "Argentina" to "ar",
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

}
