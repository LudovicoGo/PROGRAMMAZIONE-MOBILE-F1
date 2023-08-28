package com.programmazionemobile.formula1app.adapter

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStanding
import kotlin.random.Random

class DriversStandingsAdapter(val data: MutableList<DriverStanding>, val context: Context) :
    RecyclerView.Adapter<ViewHolder>() {
    //RecyclerView.Adapter<DriversStandingsAdapter.DriversStandingsViewHolder>() {
    //class DriversStandingsAdapter() :  RecyclerView.Adapter<DriversStandingsAdapter.DriversStandingsViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1

    class HeaderViewHolder(val row: View): RecyclerView.ViewHolder(row){

    }

    class DriversStandingsViewHolder(val row: View) : RecyclerView.ViewHolder(row) {

        val DriverName = row.findViewById<TextView>(R.id.DriverName)
        val DriverID = row.findViewById<TextView>(R.id.DriverID)
        val DriverFamilyName = row.findViewById<TextView>(R.id.DriverSurname)
        val DriverPosition = row.findViewById<TextView>(R.id.DriverPosition)
        val DriverNumber = row.findViewById<TextView>(R.id.DriverNumber)
        val DriverTeam = row.findViewById<TextView>(R.id.DriverTeam)
        val DriverPoints = row.findViewById<TextView>(R.id.DriverPoints)
        val DriverImage = row.findViewById<ImageView>(R.id.DriverImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        return when (viewType) {
            TYPE_ITEM -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.drivers_standings_recycler_view, parent, false)
                DriversStandingsViewHolder(itemView)
            }

            TYPE_HEADER -> {
                val headerView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_driver_standings, parent, false)
                HeaderViewHolder(headerView)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
        /*
                val layout = LayoutInflater.from(parent.context)
                    .inflate(R.layout.drivers_standings_recycler_view, parent, false)
                val holder = DriversStandingsViewHolder(layout)
                return holder*/
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        when(holder){

            is HeaderViewHolder -> {

            }

            is DriversStandingsViewHolder -> {
                if(position != TYPE_HEADER) {
                    val itemPosition = position - 1

                    holder.DriverPosition.text = data.get(itemPosition).positionText
                    holder.DriverID.text = data.get(itemPosition).driver.driverId
                    holder.DriverName.text = data[itemPosition].driver.givenName
                    holder.DriverFamilyName.text = data[itemPosition].driver.familyName
                    holder.DriverPoints.text = data[itemPosition].points
                    holder.DriverNumber.text = data[itemPosition].driver.permanentNumber


                    val driverId = data[itemPosition].driver.driverId // ID del pilota
                    val drawableResId =
                        context.resources.getIdentifier(driverId, "drawable", context.packageName)
                    if (drawableResId != 0) {
                        // Se l'ID è diverso da 0, il drawable è stato trovato
                        holder.DriverImage.setImageResource(drawableResId) // Imposta il drawable dell'ImageView
                    } else {
                        // Il drawable non è stato trovato
                    }
                    var driverConstructor = ""


                    for (constructor in data[itemPosition].constructors) {
                        driverConstructor = driverConstructor + constructor.name + " "
                    }
                    holder.DriverTeam.text = driverConstructor

                    holder.row.setOnClickListener { view ->
                        val bundle = Bundle()
                        bundle.putString("DriverID", data[itemPosition].driver.driverId)
                        bundle.putString("DriverName", data[itemPosition].driver.givenName)
                        bundle.putString("DriverFamilyName", data[itemPosition].driver.familyName)
                        bundle.putString("DriverCountry", data[itemPosition].driver.nationality)
                        bundle.putString("DriverNumber", data[itemPosition].driver.permanentNumber)
                        bundle.putString("DriverBirth", data[itemPosition].driver.dateOfBirth)
                        bundle.putString("DriverCurrentSeasonPosition", data[itemPosition].position)
                        bundle.putString("DriverPoints", data[itemPosition].points)
                        bundle.putString("DriverTeam", driverConstructor)

                        view.findNavController().navigate(R.id.driverProfileFragment, bundle)
                    }
                }
                else{
//            val items = arrayOf("Item 1", "Item 2", "Item 3") // Elenca i tuoi elementi

                    /// TODO: ddddddddddd
                }
            }
        }
    }



    override fun getItemCount(): Int {
        return data.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    fun showDriverStandings(newDriverStandings: MutableList<DriverStanding>) {
        data.clear()
        data.addAll(newDriverStandings)
        notifyDataSetChanged()
    }
}