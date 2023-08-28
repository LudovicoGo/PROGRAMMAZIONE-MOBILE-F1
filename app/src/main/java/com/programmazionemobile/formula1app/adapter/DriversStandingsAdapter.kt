package com.programmazionemobile.formula1app.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStanding

class DriversStandingsAdapter(val data: MutableList<DriverStanding>, val context: Context) :
    RecyclerView.Adapter<DriversStandingsAdapter.DriversStandingsViewHolder>() {
    //class DriversStandingsAdapter() :  RecyclerView.Adapter<DriversStandingsAdapter.DriversStandingsViewHolder>() {
    class DriversStandingsViewHolder(val row: View) : RecyclerView.ViewHolder(row) {

        val DriverName = row.findViewById<TextView>(R.id.DriverName)
        val DriverID = row.findViewById<TextView>(R.id.DriverID)
        val DriverFamilyName = row.findViewById<TextView>(R.id.DriverSurname)
        val DriverPosition = row.findViewById<TextView>(R.id.DriverPosition)
        val DriverNumber = row.findViewById<TextView>(R.id.DriverNumber)
        val DriverTeam = row.findViewById<TextView>(R.id.DriverTeam)
        val DriverPoints = row.findViewById<TextView>(R.id.DriverPoints)
        val DriverImage = row.findViewById<ImageView>(R.id.DriverImage)
//        val DriverImage = row.findViewById<ImageView>(R.id.DriverImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DriversStandingsAdapter.DriversStandingsViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.drivers_standings_recycler_view, parent, false)
        val holder = DriversStandingsViewHolder(layout)

        /*        holder.row.setOnClickListener { view ->
                    val bundle = Bundle()
                    bundle.putString("driverName", data[position].driverName)
                    bundle.putString("driverPosition", data[position].driverPosition)

                    val action =
                        DriversStandingsFragmentDirections.actionDriversStandingsFragmentToDriverProfileFragment()
        //            view.findNavController().navigate(R.id.action_driversStandingsFragment_to_driverProfileFragment)
                    view.findNavController().navigate(action)
                }*/


        return holder
    }

    override fun onBindViewHolder(
        holder: DriversStandingsAdapter.DriversStandingsViewHolder,
        position: Int
    ) {
        holder.DriverPosition.text = data.get(position).positionText
        holder.DriverID.text = data.get(position).driver.driverId
        holder.DriverName.text = data[position].driver.givenName
        holder.DriverFamilyName.text = data[position].driver.familyName
        holder.DriverPoints.text = data[position].points
        holder.DriverNumber.text = data[position].driver.permanentNumber


        val driverId = data[position].driver.driverId // ID del pilota
        val drawableResId =
            context.resources.getIdentifier(driverId, "drawable", context.packageName)
        if (drawableResId != 0) {
            // Se l'ID è diverso da 0, il drawable è stato trovato
            holder.DriverImage.setImageResource(drawableResId) // Imposta il drawable dell'ImageView
        } else {
            // Il drawable non è stato trovato
        }
        var driverConstructor = ""


        for (constructor in data[position].constructors) {
            driverConstructor = driverConstructor + constructor.name + " "
        }
        holder.DriverTeam.text = driverConstructor




        holder.row.setOnClickListener { view ->
            val bundle = Bundle()
            bundle.putString("DriverID", data[position].driver.driverId)
            bundle.putString("DriverName", data[position].driver.givenName)
            bundle.putString("DriverFamilyName", data[position].driver.familyName)
            bundle.putString("DriverCountry", data[position].driver.nationality)
            bundle.putString("DriverNumber", data[position].driver.permanentNumber)
            bundle.putString("DriverBirth", data[position].driver.dateOfBirth)
            bundle.putString("DriverCurrentSeasonPosition", data[position].position)
            bundle.putString("DriverPoints", data[position].points)
            bundle.putString("DriverTeam", driverConstructor)

            view.findNavController().navigate(R.id.driverProfileFragment, bundle)
        }





    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun showDriverStandings(newDriverStandings: MutableList<DriverStanding>) {
        data.clear()
        data.addAll(newDriverStandings)
        notifyDataSetChanged()
    }
}