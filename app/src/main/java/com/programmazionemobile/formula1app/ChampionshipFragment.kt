package com.programmazionemobile.formula1app

import ConstructorStandingsListAdapter
import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChampionshipFragment : Fragment() {

    private lateinit var viewModel: ConstructorStandingsViewModel
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_championship, container, false)

        //recyclerview
        val rv: RecyclerView = view.findViewById(R.id.constructorRecycler)
        val layoutManager = LinearLayoutManager(requireContext())

        rv.layoutManager = layoutManager

        viewModel = ViewModelProvider(this).get(ConstructorStandingsViewModel::class.java)



        //spinner
        val years = (1958..Calendar.getInstance().get(Calendar.YEAR)).toList().reversed()
        val yearsArray = years.toTypedArray()
        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.standings_spinner_closed_item_layout, yearsArray)
        spinnerAdapter.setDropDownViewResource(R.layout.years_spinner_dropdown_layout)
        val ConstructorStandingsSpinner = view.findViewById<Spinner>(R.id.constructorStandingsSpinner)

        ConstructorStandingsSpinner.adapter = spinnerAdapter
        ConstructorStandingsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = years.get(position).toString()
//                Log.d("YEAR SELECTED CONSTRUCTOR STANDINGS FRAGMENT", "${ConstructorStandingsSpinner.selectedItem}")
                viewModel.getAllConstructorStandings(item)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Non è necessario gestire questo caso
            }
        }

        ///////////////fine spinner///////////////


        viewModel.constructorStandings.observe(viewLifecycleOwner, Observer { constructorStandings ->
                val adapter = ConstructorStandingsListAdapter(requireContext(), ConstructorStandingsSpinner.selectedItem.toString())
                rv.adapter = adapter

                adapter.submitList(constructorStandings)
            })
        viewModel.getAllConstructorStandings(ConstructorStandingsSpinner.selectedItem.toString())
        return view


    }

}