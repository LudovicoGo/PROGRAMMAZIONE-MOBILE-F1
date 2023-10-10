package com.programmazionemobile.formula1app

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.adapter.QualifyingAdapter
import com.programmazionemobile.formula1app.adapter.RaceResultsAdapter
import com.programmazionemobile.formula1app.model.QualifyingViewModel
import com.programmazionemobile.formula1app.model.RaceResultsViewModel
import java.time.LocalDate

class QualifyingFragment : Fragment() {


    private val args: QualifyingFragmentArgs by navArgs()
    private lateinit var viewModel: QualifyingViewModel

    private lateinit var raceResultsNotAvailableTextView: TextView
    private lateinit var raceResultsNotAvailableOverlay: ImageView

    private lateinit var qualifyingResultsBackArrow: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_qualifying, container, false)

        raceResultsNotAvailableTextView = view.findViewById(R.id.qualifyingResultsNotAvailableTextView)
        raceResultsNotAvailableOverlay = view.findViewById(R.id.qualifyingResultsNotAvailableOverlay)

        qualifyingResultsBackArrow = view.findViewById(R.id.qualifyingResultsBackArrow)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val raceName: TextView = view.findViewById(R.id.qualifyingResultsName)
        val raceYear = LocalDate.parse(args.raceDate).year.toString()
        raceName.text = raceYear + " " + args.raceName

        qualifyingResultsBackArrow.setOnClickListener{
            findNavController().popBackStack()
        }

        viewModel = ViewModelProvider(this).get(QualifyingViewModel::class.java)


        // Trova il RecyclerView nel layout del fragment
        val rv: RecyclerView = view.findViewById(R.id.qualifyingRecyclerView)


        // Configura il LayoutManager per il RecyclerView (LinearLayoutManager in questo caso)
        val layoutManager = LinearLayoutManager(requireContext())
        rv.layoutManager = layoutManager

        viewModel.qualifyingResults.observe(viewLifecycleOwner, Observer { qualifyingResults ->

            if(qualifyingResults.size == 0){
                raceResultsNotAvailableOverlay.visibility = View.VISIBLE
                raceResultsNotAvailableTextView.visibility = View.VISIBLE
            }else {
                raceResultsNotAvailableOverlay.visibility = View.GONE
                raceResultsNotAvailableTextView.visibility = View.GONE
            }

            val adapter = QualifyingAdapter(requireContext(), qualifyingResults)
            rv.adapter = adapter
        })

        viewModel.getQualifyingResults(raceYear, args.seasonRound)

        viewModel.isInternetConnected.observe(viewLifecycleOwner) { isInternetConnected ->
            if (isInternetConnected == false) {

                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.setTitle("Can't connect to database.")

                alertDialogBuilder.setMessage("Check your internet connection!")

                alertDialogBuilder.setPositiveButton("Try again") { dialog, which ->

//                    viewModel.setInternetConnectionStatus(true)
                    viewModel.getQualifyingResults(raceYear, args.seasonRound)
                    dialog.dismiss()
                }

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }

        }
    }


}