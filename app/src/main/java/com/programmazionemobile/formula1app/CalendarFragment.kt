package com.programmazionemobile.formula1app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.adapter.CalendarAdapter
import com.programmazionemobile.formula1app.data.calendarData.Race

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

        // Configura il LayoutManager per il RecyclerView (LinearLayoutManager in questo caso)
        val layoutManager = LinearLayoutManager(requireContext())
        rv.layoutManager = layoutManager


        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)

        viewModel.calendar.observe(viewLifecycleOwner, Observer { calendar ->
            val list = mutableListOf<Race>()
            list.addAll(calendar)

            val adapter = CalendarAdapter(list, requireContext())
            rv.adapter = adapter
        })

        viewModel.getCalendar()

        return view
    }

}
