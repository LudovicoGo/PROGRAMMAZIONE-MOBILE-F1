package com.programmazionemobile.formula1app.fragment

import ConstructorStandingsListAdapter
import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.viewModel.ConstructorStandingsViewModel
import com.programmazionemobile.formula1app.R

class ChampionshipFragment : Fragment() {

    private lateinit var viewModel: ConstructorStandingsViewModel
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_championship, container, false)

        //recyclerview
        val rv: RecyclerView = view.findViewById(R.id.constructorRecycler)
        val layoutManager = LinearLayoutManager(requireContext())

        rv.layoutManager = layoutManager

        viewModel = ViewModelProvider(this).get(ConstructorStandingsViewModel::class.java)


        //spinner
        val years = (1950..Calendar.getInstance().get(Calendar.YEAR)).toList().reversed()
//        val years = (1958..2024).toList().reversed()
        val yearsArray = years.toTypedArray() //metto gli anni in un array di int

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.standings_spinner_closed_item_layout, yearsArray
        ) //creo un adapter per mostrare i dati (gli anni) nello spinner e gli passo il layout del bottone dello spinner

        spinnerAdapter.setDropDownViewResource(R.layout.years_spinner_dropdown_layout) //imposto il layout dello spinner aperto
        val ConstructorStandingsSpinner =
            view.findViewById<Spinner>(R.id.constructorStandingsSpinner) //prendo un riferimento allo spinner nel layout

        ConstructorStandingsSpinner.adapter = spinnerAdapter
        val connectionOverlayButton =
            view.findViewById<View>(R.id.connectionOverlayButton) //riferimento a tasto reload dell'overlay per quando manca la connessione

        ConstructorStandingsSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {  //listener che interviene quando si seleziona un anno dallo spinner
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item = years.get(position).toString()   //prendo l'anno selezionato
                connectionOverlayButton.setOnClickListener {    //assegno un listener al tasto "reload" dell'overlay, il tasto fa ricaricare la classifica per l'anno selezionato.
                    viewModel.getAllConstructorStandings(item)
                }

                viewModel.getAllConstructorStandings(item)  //carico la classifica per quell'anno

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Non è necessario gestire questo caso
            }
        }

        ///////////////fine spinner///////////////


        //lavoro sull'overlay
        val connectionOverlayBackground =
            view.findViewById<ImageView>(R.id.connectionOverlayBackground)
        val connectionOverlayTextView = view.findViewById<TextView>(R.id.connectionOverlayTextView)
        val connectionOverlayLogo = view.findViewById<ImageView>(R.id.connectionOverlayLogo)
        val dataNotAvailableOverlayConstructors =
            view.findViewById<TextView>(R.id.dataNotAvailableOverlayConstructors)


        viewModel.isInternetConnected.observe(viewLifecycleOwner) { isInternetConnected ->      //se ho problemi di connessione mostro l'overlay, altrimenti lo nascondo
            if (isInternetConnected == false) {

//                connectionOverlay.visibility = View.VISIBLE
                rv.visibility = View.GONE
                ConstructorStandingsSpinner.visibility = View.GONE
                connectionOverlayButton.visibility = View.VISIBLE
                connectionOverlayBackground.visibility = View.VISIBLE
                connectionOverlayTextView.visibility = View.VISIBLE
                connectionOverlayLogo.visibility = View.VISIBLE


            } else {

//                connectionOverlay.visibility = View.GONE
                rv.visibility = View.VISIBLE
                ConstructorStandingsSpinner.visibility = View.VISIBLE
                connectionOverlayTextView.visibility = View.GONE
                connectionOverlayBackground.visibility = View.GONE
                connectionOverlayLogo.visibility = View.GONE
                connectionOverlayButton.visibility = View.GONE
            }
        }


        viewModel.constructorStandings.observe(
            viewLifecycleOwner,
            Observer { constructorStandings ->   //osservo il livedata constructorStandings, quando il live data cambia allora va caricata la nuova classifica
                val adapter = ConstructorStandingsListAdapter(
                    requireContext(),
                    ConstructorStandingsSpinner.selectedItem.toString()
                )    //mostro la recyclerview della classifica per l'anno selezionato
                connectionOverlayBackground.visibility = View.GONE
                dataNotAvailableOverlayConstructors.visibility = View.GONE
                if (constructorStandings.isEmpty()) {     //se non ci sono i dati per l'anno selezionato mostro la scritta che lo dice
                    connectionOverlayBackground.visibility = View.VISIBLE
                    dataNotAvailableOverlayConstructors.visibility = View.VISIBLE
                }
                rv.adapter = adapter

                adapter.submitList(constructorStandings)
            })
        viewModel.getAllConstructorStandings(ConstructorStandingsSpinner.selectedItem.toString())   //quando apro il fragment eseguo il metodo che carica la classifica
//        viewModel.getAllConstructorStandings("2024")
        return view


    }

}