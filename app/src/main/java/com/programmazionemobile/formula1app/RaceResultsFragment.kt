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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmazionemobile.formula1app.adapter.RaceResultsAdapter
import com.programmazionemobile.formula1app.model.RaceResultsViewModel
import java.time.LocalDate

class RaceResultsFragment : Fragment() {

    private val args: RaceResultsFragmentArgs by navArgs()
    private lateinit var viewModel: RaceResultsViewModel

    private lateinit var fLapRaceResults: TextView
    private lateinit var fLapOwnerRaceResults: TextView
    private lateinit var fLapBackgroundRaceResults: ImageView
    private lateinit var fLapTextViewRaceResults: TextView
    private lateinit var raceResultsNotAvailableTextView: TextView
    private lateinit var raceResultsNotAvailableOverlay: ImageView
    private var nullFLapCounter = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_race_results, container, false)

        fLapRaceResults = view.findViewById(R.id.fLapRaceResults)
        fLapOwnerRaceResults = view.findViewById(R.id.fLapOwnerRaceResults)
        fLapBackgroundRaceResults = view.findViewById(R.id.fLapBackgroundRaceResults)
        fLapTextViewRaceResults = view.findViewById(R.id.fLapTextViewRaceResults)
        raceResultsNotAvailableTextView = view.findViewById(R.id.raceResultsNotAvailableTextView)
        raceResultsNotAvailableOverlay = view.findViewById(R.id.raceResultsNotAvailableOverlay)

        fLapTextViewRaceResults.visibility = View.GONE
        fLapOwnerRaceResults.visibility = View.GONE
        fLapTextViewRaceResults.visibility = View.GONE
        fLapBackgroundRaceResults.visibility = View.GONE
//        raceResultsNotAvailableOverlay.visibility = View.GONE

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val raceName: TextView = view.findViewById(R.id.raceResultsName)
        val raceYear = LocalDate.parse(args.raceDate).year.toString()
        raceName.text = raceYear + " " + args.raceName



        viewModel = ViewModelProvider(this).get(RaceResultsViewModel::class.java)


        // Trova il RecyclerView nel layout del fragment
        val rv: RecyclerView = view.findViewById(R.id.raceResultsRecyclerView)


        // Configura il LayoutManager per il RecyclerView (LinearLayoutManager in questo caso)
        val layoutManager = LinearLayoutManager(requireContext())
        rv.layoutManager = layoutManager

        viewModel.raceResults.observe(viewLifecycleOwner, Observer { raceResults ->

            if(raceResults.size == 0){
                raceResultsNotAvailableOverlay.visibility = View.VISIBLE
                raceResultsNotAvailableTextView.visibility = View.VISIBLE
            }else {
                raceResultsNotAvailableOverlay.visibility = View.GONE
                raceResultsNotAvailableTextView.visibility = View.GONE
                for (result in raceResults) {
                    if (result.fastestLap != null) {
                        if (result.fastestLap.rank == "1") {
                            fLapRaceResults.text = result.fastestLap.time.time
                            fLapOwnerRaceResults.text =
                                result.driver.givenName + " " + result.driver.familyName
                        }
                    } else {
                        nullFLapCounter++
                    }
                }

                if (nullFLapCounter != raceResults.size) {
                    fLapTextViewRaceResults.visibility = View.VISIBLE
                    fLapOwnerRaceResults.visibility = View.VISIBLE
                    fLapTextViewRaceResults.visibility = View.VISIBLE
                    fLapBackgroundRaceResults.visibility = View.VISIBLE
                    nullFLapCounter = 0
                }
            }
//            Log.d("RACE RESULTS FRAGMENT", raceResults.toString())

            val adapter = RaceResultsAdapter(requireContext(), raceResults)
            rv.adapter = adapter
        })

        viewModel.getRaceResults(raceYear, args.seasonRound)

    viewModel.isInternetConnected.observe(viewLifecycleOwner) { isInternetConnected ->
            if (isInternetConnected == false) {

                val alertDialogBuilder = AlertDialog.Builder(requireContext())

                alertDialogBuilder.setTitle("Can't connect to database.")

                alertDialogBuilder.setMessage("Check your internet connection!")

                alertDialogBuilder.setPositiveButton("Try again") { dialog, which ->

//                    viewModel.setInternetConnectionStatus(true)
                    viewModel.getRaceResults(raceYear, args.seasonRound)
                    dialog.dismiss()
                }

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }

        }
    }


}