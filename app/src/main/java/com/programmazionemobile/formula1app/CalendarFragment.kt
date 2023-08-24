package com.programmazionemobile.formula1app

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.adapter.CalendarAdapter
import com.programmazionemobile.formula1app.data.calendarData.Race
import java.util.Date

class CalendarFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        // Trova il RecyclerView nel layout del fragment
        val rv: RecyclerView = view.findViewById(R.id.calendar_rv)

        //val headerView = layoutInflater.inflate(R.layout.header_race_recycler_view, container, false)

        /*val nomeNextGara = headerView.findViewById<TextView>(R.id.nomeProssimaGara)
        val dataNextGara = headerView.findViewById<TextView>(R.id.dataProssimaGara)
        val descNextGara = headerView.findViewById<TextView>(R.id.descrizioneProssimaGara)
        val flagNextGara = headerView.findViewById<ImageView>(R.id.flagProssimaGara)*/

        /*val nomeNextGara = view.findViewById<TextView>(R.id.nomeProssimaGara)
        val dataNextGara = view.findViewById<TextView>(R.id.dataProssimaGara)
        val descNextGara = view.findViewById<TextView>(R.id.descrizioneProssimaGara)
        val flagNextGara = view.findViewById<ImageView>(R.id.flagProssimaGara)*/
        

        // Configura il LayoutManager per il RecyclerView (LinearLayoutManager in questo caso)
        val layoutManager = LinearLayoutManager(requireContext())
        rv.layoutManager = layoutManager


        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)

        viewModel.calendar.observe(viewLifecycleOwner) { calendar ->
            val list = mutableListOf<Race>()
            list.addAll(calendar)

            /*nomeNextGara.text = nextRace(calendar)?.raceName
            dataNextGara.text = DateConverter.convertDate(nextRace(calendar)?.date!!)
            descNextGara.text = nextRace(calendar)?.circuit?.circuitName

            val countryName = nextRace(calendar)?.circuit?.location?.country
            val countryCode = getCountryCode(countryName!!)

            flagNextGara.load("https://flagpedia.net/data/flags/w1160/$countryCode.webp"){
                transformations(RoundedCornersTransformation(75f, 30f, 0f, 30f))
            }*/

            val adapter = CalendarAdapter(list, requireContext())
            //rv.adapter = HeaderRaceAdapter(headerView, adapter)
            rv.adapter = adapter
        }


        viewModel.getCalendar()

        return view
    }

    fun nextRace(gare: List<Race>): Race? {
        // Ottieni la data corrente
        val dataCorrente = Date()

        // Inizializza la gara più prossima
        var garaProssima: Race? = null
        var dataProssima: Date? = null

        // Formato per il parsing delle date
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")

        // Itera attraverso tutte le gare nella collezione
        for (gara in gare) {
            val dataGara = dateFormat.parse(gara.date)

            // Se la data della gara è nel futuro e più vicina alla data corrente,
            // impostala come gara prossima
            if (dataGara >= dataCorrente && (dataProssima == null || dataGara.before(dataProssima))) {
                garaProssima = gara
                dataProssima = dataGara
            }
        }
        return garaProssima
    }
}
