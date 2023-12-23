package com.programmazionemobile.formula1app.fragment

import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.viewModel.DriverStandingsViewModel
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.adapter.DriversStandingsAdapter
import com.programmazionemobile.formula1app.data.driverStandingsData.DriverStanding


/*

<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
*/


class DriversStandingsFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {


        }*/
    }

    private lateinit var viewModel: DriverStandingsViewModel
    private lateinit var textView: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_drivers_standings, container, false)

        val rv: RecyclerView = view.findViewById(R.id.driversRecycler)
        val layoutManager = LinearLayoutManager(requireContext())

        rv.layoutManager = layoutManager

        // Inizializza il ViewModel
        viewModel = ViewModelProvider(this).get(DriverStandingsViewModel::class.java)
        // Osserva il LiveData driverStandings

        //spinner
        val years = (1950..Calendar.getInstance().get(Calendar.YEAR)).toList().reversed()
//        val years = (1950..2024).toList().reversed()////////////////////////////////////////////////////////////////////
        val yearsArray = years.toTypedArray()

        val spinnerAdapter = ArrayAdapter(requireContext(),
            R.layout.standings_spinner_closed_item_layout, yearsArray)
        spinnerAdapter.setDropDownViewResource(R.layout.years_spinner_dropdown_layout)
        val DriversStandingsSpinner = view.findViewById<Spinner>(R.id.DriversStandingsSpinner)

        DriversStandingsSpinner.adapter = spinnerAdapter

        val connectionOverlayButton = view.findViewById<View>(R.id.connectionOverlayButton)

        DriversStandingsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = years.get(position).toString()
                // Chiama la funzione passata come parametro
//                Log.d("YEAR SELECTED FRAGMENT", "${DriversStandingsSpinner.selectedItem}")
                viewModel.getAllDriverStandings(item)
                connectionOverlayButton.setOnClickListener {
                    viewModel.getAllDriverStandings(item)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Non è necessario gestire questo caso
            }
        }



        val connectionOverlayBackground = view.findViewById<ImageView>(R.id.connectionOverlayBackground)
        val connectionOverlayTextView = view.findViewById<TextView>(R.id.connectionOverlayTextView)
        val connectionOverlayLogo = view.findViewById<ImageView>(R.id.connectionOverlayLogo)
        val dataNotAvailableOverlayDrivers = view.findViewById<TextView>(R.id.dataNotAvailableOverlayDrivers)


        viewModel.driverStandings.observe(viewLifecycleOwner, Observer { driverStandings ->
            val lista = mutableListOf<DriverStanding>()
            connectionOverlayBackground.visibility = View.GONE
            dataNotAvailableOverlayDrivers.visibility = View.GONE

            if(driverStandings.isEmpty()){
                connectionOverlayBackground.visibility = View.VISIBLE
                dataNotAvailableOverlayDrivers.visibility = View.VISIBLE
            }
                lista.addAll(driverStandings)

                val adapter = DriversStandingsAdapter(
                    lista,
                    requireContext(),
                    DriversStandingsSpinner.selectedItem.toString()
                )

                rv.adapter = adapter
        })

        val progressBar = view.findViewById<ProgressBar>(R.id.DriverStandingsProgressBar)
        val progressBarTextView = view.findViewById<TextView>(R.id.DriverStandingsProgressBarText)
        val progressBarBackground = view.findViewById<ImageView>(R.id.DriverStandingsProgressBarBackground)
        val progressOverlay = view.findViewById<View>(R.id.DriverStandingsprogressOverlay)


        viewModel.loadingState.observe(viewLifecycleOwner, Observer { isLoading ->

            if (isLoading) {

                connectionOverlayBackground.visibility = View.GONE
                dataNotAvailableOverlayDrivers.visibility = View.GONE


                //tolgo la possibilità di interagire con le viste durante il caricamento
                getActivity()?.getWindow()?.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                //mostro la progressbar e il testo
                progressOverlay.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
                progressBarBackground.visibility = View.VISIBLE
                progressBarTextView.visibility = View.VISIBLE


            } else {



                //rimetto  la possibilità di interagire con le viste durante il caricamento
                getActivity()?.getWindow()?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                //nascondo la progressbar e il testo
                progressOverlay.visibility = View.GONE
                progressBar.visibility = View.GONE
                progressBarBackground.visibility = View.GONE
                progressBarTextView.visibility = View.GONE
            }
        })




        viewModel.isInternetConnected.observe(viewLifecycleOwner) { isInternetConnected ->
            if (isInternetConnected == false) {

//                connectionOverlay.visibility = View.VISIBLE
                rv.visibility = View.GONE
                DriversStandingsSpinner.visibility = View.GONE
                connectionOverlayButton.visibility = View.VISIBLE
                connectionOverlayBackground.visibility = View.VISIBLE
                connectionOverlayTextView.visibility = View.VISIBLE
                connectionOverlayLogo.visibility = View.VISIBLE


            } else {

//                connectionOverlay.visibility = View.GONE
                rv.visibility = View.VISIBLE
                DriversStandingsSpinner.visibility = View.VISIBLE
                connectionOverlayTextView.visibility = View.GONE
                connectionOverlayBackground.visibility = View.GONE
                connectionOverlayLogo.visibility = View.GONE
                connectionOverlayButton.visibility = View.GONE
            }
        }
        //chiamata per ottenere i dati sulla classifica dei piloti
        val currentYear = Calendar.getInstance().get(Calendar.YEAR).toString()
//        val currentYear = "2024"
        viewModel.getAllDriverStandings(currentYear)

        return view

//        return inflater.inflate(R.layout.fragment_drivers_standings, container, false)
    }
}