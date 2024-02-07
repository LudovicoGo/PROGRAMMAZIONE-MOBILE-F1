package com.programmazionemobile.formula1app.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.programmazionemobile.formula1app.R // Assicurati che il percorso sia corretto
import com.programmazionemobile.formula1app.data.constructorDriversData.Driver

class ConstructorProfileDriversAdapter(
    private val context: Context,
    private val driverList: List<Driver>) : BaseAdapter() {

    override fun getCount(): Int {
        return driverList.size
    }

    override fun getItem(position: Int): Any {
        return driverList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val driver = getItem(position) as Driver
        Log.d("adapter", driver.toString())

        val inflater = LayoutInflater.from(context)
        val view =
            convertView ?: inflater.inflate(R.layout.constructor_drivers_listview, parent, false)

        val driverNameTextView = view.findViewById<TextView>(R.id.DriverName)
        val driverSurnameTextView = view.findViewById<TextView>(R.id.DriverSurname)
        val driverNumberTextView = view.findViewById<TextView>(R.id.DriverNumber)
        val driverImageView = view.findViewById<ImageView>(R.id.DriverImage)

        driverNameTextView.text = "${driver.givenName}"
        driverNumberTextView.text = "# ${driver.permanentNumber}"
        driverSurnameTextView.text = "${driver.familyName}"

        if (driver.permanentNumber != null && driver.permanentNumber != "noDriverNumber") {
            driverNumberTextView.text = "# ${driver.permanentNumber}"
        } else {
            driverNumberTextView.text = ""

        }

        val drawableResId = context.resources.getIdentifier(driver.driverId, "drawable", context.packageName)

        if (drawableResId != 0) {
            // Se l'ID è diverso da 0, il drawable è stato trovato
            driverImageView.setImageResource(drawableResId) // Imposta il drawable dell'ImageView
        } else {
            driverImageView.setImageResource(R.drawable.nodriverpic)
        }

        return view
    }
}
