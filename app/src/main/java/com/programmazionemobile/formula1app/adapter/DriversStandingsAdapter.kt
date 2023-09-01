package com.programmazionemobile.formula1app.adapter

import android.content.Context
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStanding

class DriversStandingsAdapter(
    val data: MutableList<DriverStanding>,
    val context: Context,
    val selectedYearSpinner: String
) :
    RecyclerView.Adapter<ViewHolder>() {
    //RecyclerView.Adapter<DriversStandingsAdapter.DriversStandingsViewHolder>() {
    //class DriversStandingsAdapter() :  RecyclerView.Adapter<DriversStandingsAdapter.DriversStandingsViewHolder>() {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private var spinnerFlag = 0
    private var spinnerYearFlag = Calendar.getInstance().get(Calendar.YEAR).toString()

    class HeaderViewHolder(val row: View) : RecyclerView.ViewHolder(row) {

        val DriverStandingsYearSpinner = row.findViewById<Spinner>(R.id.DriverStandingsYearSpinner)
        val DriverStandingsYearSpinnerText =
            row.findViewById<TextView>(R.id.DriverStandingsYearSpinnerText)


        init {

        }
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

        val BottomBorderImage = row.findViewById<ImageView>(R.id.BottomBorderStandings)
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
                HeaderViewHolder(headerView) //passo anche callback
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


        when (holder) {

            is HeaderViewHolder -> {

            }

            is DriversStandingsViewHolder -> {
                if (position != TYPE_HEADER) {

                    val itemPosition = position - 1

                    holder.DriverPosition.text = data.get(position - 1).positionText
                    holder.DriverID.text = data.get(position - 1).driver.driverId
                    holder.DriverName.text = data[position - 1].driver.givenName
                    holder.DriverFamilyName.text = data[position - 1].driver.familyName
                    holder.DriverPoints.text = data[position - 1].points
                    if (!data[position - 1].driver.permanentNumber.isNullOrEmpty()) {
//                            bundle.putString("DriverNumber", data[position - 1].driver.permanentNumber)
                        holder.DriverNumber.text = data[position - 1].driver.permanentNumber
                    } else{
                        holder.DriverNumber.text = ""
                    }
//                    holder.DriverNumber.text = data[position - 1].driver.permanentNumber
//                    val seasonWins = data[position - 1].wins


                    val driverId = data[position - 1].driver.driverId // ID del pilota
                    val drawableResId =
                        context.resources.getIdentifier(driverId, "drawable", context.packageName)
                    if (drawableResId != 0) {
                        // Se l'ID è diverso da 0, il drawable è stato trovato
                        holder.DriverImage.setImageResource(drawableResId) // Imposta il drawable dell'ImageView
                    } else {
                        holder.DriverImage.setImageResource(R.drawable.nodriverpic)
                    }



//                    var driverConstructor = data[position - 1].constructors[0].name
                    var driverConstructor = ""


                    for (constructor in data[position - 1].constructors) {
                        driverConstructor = driverConstructor + constructor.name + " "
                    }
                    holder.DriverTeam.text = driverConstructor
//
//                    val backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.standings_bottom_border)
//                    backgroundDrawable?.setTint(0xFF00FF00.toInt())

                    //bordo inferiore
                    //prendo il colore del team dal file colors.xml
                    val teamColorResId = context.resources.getIdentifier(
                        "${driverConstructor.replace(" ", "")}",
                        "color",
                        context.packageName
                    )
                    if (teamColorResId != 0) {
                        val teamColor = ContextCompat.getColor(context, teamColorResId)

                        // Cambiare il colore del drawable di sfondo
                        val backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.standings_bottom_border)
                        backgroundDrawable?.setTint(teamColor)
//                            .setColorFilter(teamColor, PorterDuff.Mode.SRC_IN)


                        holder.BottomBorderImage.background = backgroundDrawable
                    }



                    holder.row.setOnClickListener { view ->
                        val bundle = Bundle()
                        val selectedYear =
                            view.findViewById<Spinner>(R.id.DriverStandingsYearSpinner)
                        bundle.putString("DriverID", data[position - 1].driver.driverId)
                        bundle.putString("DriverName", data[position - 1].driver.givenName)
                        bundle.putString("DriverFamilyName", data[position - 1].driver.familyName)
                        bundle.putString("DriverCountry", data[position - 1].driver.nationality)
                        if (!data[position - 1].driver.permanentNumber.isNullOrEmpty()) {
                            bundle.putString("DriverNumber", data[position - 1].driver.permanentNumber)
                        }
                        bundle.putString("DriverBirth", data[position - 1].driver.dateOfBirth)
                        bundle.putString("DriverCurrentSeasonPosition", data[position - 1].position)
                        bundle.putString("DriverPoints", data[position - 1].points)
                        bundle.putString("DriverTeam", driverConstructor)
                        bundle.putString("SelectedYearSpinner", selectedYearSpinner)

                        view.findNavController().navigate(R.id.driverProfileFragment, bundle)
                    }
                } else {

                    /// TODO: ddddddddddd
                }

            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return data.size + 1
    }

    fun showDriverStandings(newDriverStandings: MutableList<DriverStanding>) {
        data.clear()
        data.addAll(newDriverStandings)
        notifyDataSetChanged()
    }
}