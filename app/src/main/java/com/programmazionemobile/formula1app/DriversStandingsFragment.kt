package com.programmazionemobile.formula1app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.adapter.DriversStandingsAdapter


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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_drivers_standings, container, false)

        // Trova il RecyclerView nel layout del fragment
        val rv: RecyclerView = view.findViewById(R.id.driversRecycler)

        // Configura il LayoutManager per il RecyclerView (LinearLayoutManager in questo caso)
        val layoutManager = LinearLayoutManager(requireContext())
        rv.layoutManager = layoutManager

        // Crea l'adapter e assegna al RecyclerView
        val data = IntRange(0, 10).toList()
        val adapter = DriversStandingsAdapter(data)
        rv.adapter = adapter

        return view

//        return inflater.inflate(R.layout.fragment_drivers_standings, container, false)
    }
}