package com.programmazionemobile.formula1app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.programmazionemobile.formula1app.model.CircuitInfoViewModel


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

        viewModel = ViewModelProvider(this).get(CircuitInfoViewModel::class.java)

        circuit.text = viewModel.getCircuitName("spa")
    }

}