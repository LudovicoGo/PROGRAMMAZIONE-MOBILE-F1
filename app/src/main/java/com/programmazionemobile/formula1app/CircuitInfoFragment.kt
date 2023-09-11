package com.programmazionemobile.formula1app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import coil.load
import com.programmazionemobile.formula1app.model.CircuitInfoViewModel
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val circuit = view.findViewById<TextView>(R.id.nomeGara2)
        val laps = view.findViewById<TextView>(R.id.n_laps)
        val lapDistance = view.findViewById<TextView>(R.id.lap_distance)
        val raceDistance = view.findViewById<TextView>(R.id.raceDistance)
        val firstGP = view.findViewById<TextView>(R.id.firstGP)
        val fastestLap = view.findViewById<TextView>(R.id.lapRecord)
        val driverFatsLap = view.findViewById<TextView>(R.id.lapRecordDriver)
        val circuitoPng = view.findViewById<ImageView>(R.id.circuito)

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
                    lapDistance.text = innerObject.getString("Lunghezza") + " km"
                    laps.text = innerObject.getInt("Numero di Giri").toString()
                    val km = String.format("%.3f", (innerObject.getInt("Numero di Giri")
                        .times(innerObject.getDouble("Lunghezza"))))
                    raceDistance.text = "$km km"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        viewModel.getFirstGPLiveData(args.circuitID).observe(viewLifecycleOwner) { firstGPData ->
            firstGP.text = firstGPData
        }

        if (firstGP.text.toString() < "2023"){
            viewModel.getFastestLap(args.circuitID).observe(viewLifecycleOwner) { firstGPData ->
                fastestLap.text = firstGPData
            }
            viewModel.getDriverFastLap(args.circuitID).observe(viewLifecycleOwner) { firstGPData ->
                driverFatsLap.text = firstGPData
            }
        }
        else {
            fastestLap.text = "GP non ancora disputato"
            driverFatsLap.text = ""
        }

        circuitoPng.load("https://media.formula1.com/image/upload/content" +
                "/dam/fom-website/2018-redesign-assets/Circuit%20maps%2016x9/${getCircuitCode(args.circuitID)}_Circuit.png." +
                "transform/7col-retina/image.png")
    }

    fun getCircuitCode(circuitID: String): String? {
        val countryNameToCodeMap = mapOf(
            "monza" to "Italy",
            "jeddah" to "Abu_Dhabi",
            "bahrain" to "Bahrain",
            "spa" to "Belgium",
            "albert_park" to "Australia",
            "miami" to "Miami",
            "monaco" to "Monoco",
            "catalunya" to "Spain",
            "villeneuve" to "Canada",
            "red_bull_ring" to "Austria",
            "silverstone" to "Great_Britain",
            "hungaroring" to "Hungary",
            "zandvoort" to "Netherlands",
            "marina_bay" to "Singapore",
            "suzuka" to "Japan",
            "losail" to "Qatar",
            "americas" to "USA",
            "rodriguez" to "Mexico",
            "interlagos" to "Brazil",
            "vegas" to "Las_Vegas",
            "yas_marina" to "Abu_Dhabi",
            "ricard" to "France"
        )
        return countryNameToCodeMap[circuitID]
    }
}