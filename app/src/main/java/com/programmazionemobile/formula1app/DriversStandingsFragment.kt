package com.programmazionemobile.formula1app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.adapter.DriversStandingsAdapter
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStanding


/*

<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
*/


class DriversStandingsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {


        }*/
    }
    private lateinit var viewModel: DriverStandingsViewModel
    private lateinit var textView: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_drivers_standings, container, false)

        val rv: RecyclerView = view.findViewById(R.id.driversRecycler)
        val layoutManager = LinearLayoutManager(requireContext())
        rv.layoutManager = layoutManager

        // Inizializza il ViewModel
        viewModel = ViewModelProvider(this).get(DriverStandingsViewModel::class.java)

/*
        val spinner: Spinner = view.findViewById(R.id.)

        // Dati da visualizzare nello Spinner
        val spinnerItems = listOf("Item 1", "Item 2", "Item 3")

        // Creare l'ArrayAdapter e collegarlo allo Spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, spinnerItems)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
*/


        // Osserva il LiveData driverStandings
        viewModel.driverStandings.observe(viewLifecycleOwner, Observer { driverStandings ->
            val lista = mutableListOf<DriverStanding>()
            lista.addAll(driverStandings)

            val adapter = DriversStandingsAdapter(lista, requireContext())
            rv.adapter = adapter
        })
        // Chiamata per ottenere i dati sulla classifica dei piloti
        viewModel.getAllDriverStandings()

        return view

//        return inflater.inflate(R.layout.fragment_drivers_standings, container, false)
    }
}