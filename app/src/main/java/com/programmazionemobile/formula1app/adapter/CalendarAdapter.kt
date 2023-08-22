package com.programmazionemobile.formula1app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.view.ViewGroup
import android.view.animation.Transformation
import android.widget.ImageView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.programmazionemobile.formula1app.DateConverter
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.data.calendarData.Race
import java.util.Locale

class CalendarAdapter(val data: MutableList<Race>, val context: Context) :  RecyclerView.Adapter<CalendarAdapter.RaceListViewHolder>() {

    class RaceListViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val dataGara = row.findViewById<TextView>(R.id.dataGara)
        val nomeGara = row.findViewById<TextView>(R.id.nomeGara)
        val descGara = row.findViewById<TextView>(R.id.descrizioneGara)
        val flagGara = row.findViewById<ImageView>(R.id.flagGara)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.RaceListViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.race_recycler_view, parent, false)
        RaceListViewHolder(layout).row.setOnClickListener{
                view -> view.findNavController().navigate(R.id.action_calendarFragment_to_raceFragment2)
        }
        return RaceListViewHolder(layout)
    }

    override fun onBindViewHolder(holder: CalendarAdapter.RaceListViewHolder, round: Int) {

        holder.dataGara.text = DateConverter.convertDate(data.get(round).date)
        holder.nomeGara.text = data.get(round).raceName
        holder.descGara.text = data.get(round).circuit.circuitName
        val countryName = data.get(round).circuit.location.country

        val countryCode = getCountryCode(countryName)

        holder.flagGara.load("https://flagpedia.net/data/flags/w1160/$countryCode.webp"){
            transformations(RoundedCornersTransformation(75F, 30f, 0f, 30f))
        }
    }
    override fun getItemCount(): Int = data.size

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
            "Qatar" to "qa"
        )
        return countryNameToCodeMap[countryName]
    }


    /*override fun getItemCount(): Int {

    }*/
}