package com.programmazionemobile.formula1app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.data.raceResultsData.Result

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.programmazionemobile.formula1app.R

class RaceResultsAdapter(
    private val context: Context,
    private val data: List<Result>
) : RecyclerView.Adapter<RaceResultsAdapter.RaceResultsViewHolder>() {

    inner class RaceResultsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val positionRaceResults: TextView = itemView.findViewById(R.id.positionQualifying)
        val driverRaceResults: TextView = itemView.findViewById(R.id.driverQualifying)
        val pointsRaceResults: TextView = itemView.findViewById(R.id.pointsRaceResults)
        val timeRaceResults: TextView = itemView.findViewById(R.id.timeQ1)
        val rightBorderElement: ConstraintLayout = itemView.findViewById(R.id.rightBorderElement)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaceResultsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.race_results_listview, parent, false)
        return RaceResultsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RaceResultsViewHolder, position: Int) {

        holder.positionRaceResults.text = data[position].positionText
        holder.driverRaceResults.text = data[position].driver.familyName
        holder.pointsRaceResults.text = data[position].points


        if(data[position].time != null) {
            holder.timeRaceResults.text = data[position].time.time
        }else{
            holder.timeRaceResults.text = data[position].status
        }


        //bordo destro
        //prendo il colore del team dal file colors.xml
            if (context.resources.getIdentifier("${data[position].constructor.constructorId.replace("-","_")}","color", context.packageName) != 0) {
                var teamColorResId = context.resources.getIdentifier("${data[position].constructor.constructorId.replace("-","_")}","color", context.packageName)

//                Log.d("teamcolorresId", teamColorResId.toString())

                val teamColor = ContextCompat.getColor(context, teamColorResId)

                // Cambiare il colore del drawable di sfondo
                val backgroundDrawable = ContextCompat.getDrawable(context, R.drawable.standings_bottom_border)
                backgroundDrawable?.setTint(teamColor)
                holder.rightBorderElement.background = backgroundDrawable
            }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}