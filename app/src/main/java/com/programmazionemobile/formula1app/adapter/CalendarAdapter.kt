package com.programmazionemobile.formula1app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.data.calendarData.Race

class CalendarAdapter(val data: MutableList<Race>, val context: Context) :  RecyclerView.Adapter<CalendarAdapter.RaceListViewHolder>() {
    class RaceListViewHolder(val row: View) : RecyclerView.ViewHolder(row) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.RaceListViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.race_recycler_view, parent, false)
        RaceListViewHolder(layout).row.setOnClickListener{
                view -> view.findNavController().navigate(R.id.action_calendarFragment_to_raceFragment2)
        }
        return RaceListViewHolder(layout)
    }

    override fun onBindViewHolder(holder: CalendarAdapter.RaceListViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = data.size

    /*override fun getItemCount(): Int {

    }*/
}