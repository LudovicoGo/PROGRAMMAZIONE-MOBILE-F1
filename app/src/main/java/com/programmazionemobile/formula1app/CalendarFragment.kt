package com.programmazionemobile.formula1app

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.adapter.CalendarAdapter
import com.programmazionemobile.formula1app.data.calendarData.Race
import com.programmazionemobile.formula1app.model.CalendarViewModel

class CalendarFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        val headerView = inflater.inflate(R.layout.header_race_recycler_view, container, false)

        // Trova il RecyclerView nel layout del fragment
        val rv: RecyclerView = view.findViewById(R.id.calendar_rv)


        // Configura il LayoutManager per il RecyclerView (LinearLayoutManager in questo caso)
        val layoutManager = LinearLayoutManager(requireContext())
        rv.layoutManager = layoutManager

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)

        val years = (1950..Calendar.getInstance().get(Calendar.YEAR)).toList().reversed()
        val yearsArray = years.toTypedArray()

        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)

        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.standings_spinner_closed_item_layout, yearsArray)
        spinnerAdapter.setDropDownViewResource(R.layout.years_spinner_dropdown_layout)
        val calendarSpinner = view.findViewById<Spinner>(R.id.spinner)

        calendarSpinner.adapter = spinnerAdapter
        val connectionOverlayButton = view.findViewById<View>(R.id.connectionOverlayButton)

        calendarSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = yearsArray.get(position)
                // Chiama la funzione passata come parametro
//                Log.d("YEAR SELECTED FRAGMENT", "${DriversStandingsSpinner.selectedItem}")
                viewModel.getCalendar(item.toString())

                connectionOverlayButton.setOnClickListener {
                    viewModel.getCalendar(item.toString())
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Non è necessario gestire questo caso
            }
        }


        viewModel.calendar.observe(viewLifecycleOwner) { calendar ->
            val list = mutableListOf<Race>()
            list.addAll(calendar)

            val adapter = CalendarAdapter(list, requireContext())
            //rv.adapter = HeaderRaceAdapter(headerView, adapter)
            rv.adapter = adapter
        }




        val connectionOverlayBackground = view.findViewById<ImageView>(R.id.connectionOverlayBackground)
        val connectionOverlayTextView = view.findViewById<TextView>(R.id.connectionOverlayTextView)
        val connectionOverlayLogo = view.findViewById<ImageView>(R.id.connectionOverlayLogo)


        viewModel.isInternetConnected.observe(viewLifecycleOwner) { isInternetConnected ->
            if (isInternetConnected == false) {

//                connectionOverlay.visibility = View.VISIBLE
                rv.visibility = View.GONE
                connectionOverlayButton.visibility = View.VISIBLE
                connectionOverlayBackground.visibility = View.VISIBLE
                connectionOverlayTextView.visibility = View.VISIBLE
                connectionOverlayLogo.visibility = View.VISIBLE


            } else {

//                connectionOverlay.visibility = View.GONE
                rv.visibility = View.VISIBLE
                connectionOverlayTextView.visibility = View.GONE
                connectionOverlayBackground.visibility = View.GONE
                connectionOverlayLogo.visibility = View.GONE
                connectionOverlayButton.visibility = View.GONE
            }
        }





        viewModel.getCalendar(currentYear.toString())

        return view
    }

}
