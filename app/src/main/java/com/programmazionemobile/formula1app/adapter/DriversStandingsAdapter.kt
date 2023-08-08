package com.programmazionemobile.formula1app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.R

class DriversStandingsAdapter(val data: List<Int>) :  RecyclerView.Adapter<DriversStandingsAdapter.DriversStandingsViewHolder>() {
    class DriversStandingsViewHolder(val row: View) : RecyclerView.ViewHolder(row) {

        val DriverName = row.findViewById<TextView>(R.id.DriverPosition)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversStandingsAdapter.DriversStandingsViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.drivers_standings_recycler_view, parent, false)
        return DriversStandingsViewHolder(layout)
    }

    override fun onBindViewHolder(holder: DriversStandingsAdapter.DriversStandingsViewHolder, position: Int) {
        holder.DriverName.text = data.get(position).toString()
    }

    override fun getItemCount(): Int = data.size

    /*override fun getItemCount(): Int {

    }*/
}