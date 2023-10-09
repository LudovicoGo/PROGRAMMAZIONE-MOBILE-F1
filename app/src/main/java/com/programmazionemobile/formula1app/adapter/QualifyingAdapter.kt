package com.programmazionemobile.formula1app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.data.qualifyingData.QualifyingResult

class QualifyingAdapter(
    private val context: Context,
    private val data: List<QualifyingResult>
) : RecyclerView.Adapter<QualifyingAdapter.QualifyingViewHolder>()  {


    inner class QualifyingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val positionQualifying: TextView = itemView.findViewById(R.id.positionQualifying)
        val driverRQualifying: TextView = itemView.findViewById(R.id.driverQualifying)
        val timeQualifying1: TextView = itemView.findViewById(R.id.timeQ1)
        val timeQualifying2: TextView = itemView.findViewById(R.id.timeQ2)
        val timeQualifying3: TextView = itemView.findViewById(R.id.timeQ3)
        val rightBorderElement: ConstraintLayout = itemView.findViewById(R.id.rightBorderElement)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QualifyingViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.qualifying_results_listview, parent, false)
        return QualifyingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QualifyingViewHolder, position: Int) {

        holder.positionQualifying.text = data[position].position
        holder.driverRQualifying.text = data[position].driver.familyName
//        holder.timeQualifying2.text = data[position].q2
//        holder.timeQualifying3.text = data[position].q3


        if(data[position].q1 != null) {
            holder.timeQualifying1.text = data[position].q1
        }else{
            holder.timeQualifying1.text = "-"
        }
        if(data[position].q2 != null) {
            holder.timeQualifying2.text = data[position].q2
        }else{
            holder.timeQualifying2.text = "-"
        }
        if(data[position].q3 != null) {
            holder.timeQualifying3.text = data[position].q3
        }else{
            holder.timeQualifying3.text = "-"
        }


        //bordo destro
        //prendo il colore del team dal file colors.xml
        if (context.resources.getIdentifier("${data[position].constructor.constructorId.replace("-","_")}","color", context.packageName) != 0) {
            var teamColorResId = context.resources.getIdentifier("${data[position].constructor.constructorId.replace("-","_")}","color", context.packageName)

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