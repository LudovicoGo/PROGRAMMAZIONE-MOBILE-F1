package com.programmazionemobile.formula1app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.R

class DriversStandingsAdapter(val data: List<Int>) :  RecyclerView.Adapter<DriversStandingsAdapter.DriversStandingsViewHolder>() {
    class DriversStandingsViewHolder(val row: View) : RecyclerView.ViewHolder(row) {

        val DriverName = row.findViewById<TextView>(R.id.DriverPosition)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DriversStandingsAdapter.DriversStandingsViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.drivers_standings_recycler_view, parent, false)
        val holder =  DriversStandingsViewHolder(layout)
        holder.row.setOnClickListener{
                view -> view.findNavController().navigate(R.id.action_driversStandingsFragment_to_driverProfileFragment)
            /*
            val navController = findNavController()
            // Esegui la navigazione verso il FragmentB
            navController.navigate(R.id.action_driversStandingsFragment_to_driverProfileFragment)*/
        }
        return holder
    }

    override fun onBindViewHolder(holder: DriversStandingsAdapter.DriversStandingsViewHolder, position: Int) {
        holder.DriverName.text = data.get(position).toString()
    }

    override fun getItemCount(): Int = data.size

    /*override fun getItemCount(): Int {

    }*/
}