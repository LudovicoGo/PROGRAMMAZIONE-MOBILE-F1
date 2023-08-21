package com.programmazionemobile.formula1app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.programmazionemobile.formula1app.adapter.CalendarAdapter

class RaceFragment: Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val thisView = inflater.inflate(R.layout.fragment_race_info, container, false)

        thisView.findViewById<ImageView>(R.id.infoCircuitoCard).setOnClickListener{
                view-> view.findNavController().navigate(R.id.action_raceFragment2_to_circuitInfoFragment)
        }

        return thisView
    }

}