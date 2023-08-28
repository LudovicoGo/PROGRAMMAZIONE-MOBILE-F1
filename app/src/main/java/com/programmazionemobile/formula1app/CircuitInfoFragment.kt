package com.programmazionemobile.formula1app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.programmazionemobile.formula1app.model.CircuitInfoViewModel
import org.json.JSONArray
import org.json.JSONObject


class CircuitInfoFragment: Fragment() {

    private lateinit var viewModel: CircuitInfoViewModel

    private val args: CircuitInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circuit_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val circuit = view.findViewById<TextView>(R.id.nomeGara2)
        val laps = view.findViewById<TextView>(R.id.n_laps)
        val lapDistance = view.findViewById<TextView>(R.id.lap_distance)
        val raceDistance = view.findViewById<TextView>(R.id.raceDistance)

        viewModel = ViewModelProvider(this).get(CircuitInfoViewModel::class.java)

        circuit.text = args.circuitName
        try {
            val jsonString = context?.assets?.open("circuiti.json")?.bufferedReader().use { it?.readText() }

            val jsonObject = JSONObject(jsonString!!)

            // Itera sugli oggetti nel JSON
            for (key in jsonObject.keys()) {
                val innerObject = jsonObject.getJSONObject(key)

                // Esempio: Estrai i dati dall'oggetto interno
                if(args.circuitID == key){
                    lapDistance.text = innerObject.getDouble("Lunghezza").toString() + " km"
                    laps.text = innerObject.getInt("Numero di Giri").toString()
                    raceDistance.text = (innerObject.getInt("Numero di Giri")
                        .times(innerObject.getDouble("Lunghezza"))).toString() + " km"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}