package com.programmazionemobile.formula1app.fragment

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
import com.programmazionemobile.formula1app.R
import com.programmazionemobile.formula1app.adapter.RaceResultsAdapter
import com.programmazionemobile.formula1app.adapter.SprintResultsAdapter
import com.programmazionemobile.formula1app.viewModel.RaceResultsViewModel
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


    private lateinit var raceButton: ImageView
    private lateinit var sprintButton: ImageView
    private lateinit var raceBlackLine: ImageView
    private lateinit var sprintBlackLine: ImageView
    private lateinit var raceResultsBackArrow: ImageView
    private lateinit var sprintResultsText: TextView
    private lateinit var raceResultsText: TextView


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
        raceResultsBackArrow = view.findViewById(R.id.RaceResultsBackArrow)

        fLapTextViewRaceResults.visibility = View.GONE
        fLapOwnerRaceResults.visibility = View.GONE
        fLapTextViewRaceResults.visibility = View.GONE
        fLapBackgroundRaceResults.visibility = View.GONE
//        raceResultsNotAvailableOverlay.visibility = View.GONE


        raceButton = view.findViewById(R.id.raceResultsButton)
        sprintButton = view.findViewById(R.id.sprintResultsButton)
        raceBlackLine = view.findViewById(R.id.raceResultsBlackLine)
        sprintBlackLine = view.findViewById(R.id.sprintResultsBlackLine)
        sprintBlackLine.visibility = View.GONE
        sprintResultsText = view.findViewById(R.id.sprintResultsButtonText)
        raceResultsText = view.findViewById(R.id.raceResultsButtonText)




        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val raceName: TextView = view.findViewById(R.id.raceResultsName)
        val raceYear = LocalDate.parse(args.raceDate).year.toString()
        raceName.text = raceYear + " " + args.raceName

        raceResultsBackArrow.setOnClickListener{
            findNavController().popBackStack()
        }

        viewModel = ViewModelProvider(this).get(RaceResultsViewModel::class.java)


        // Trova il RecyclerView nel layout del fragment
        val rv: RecyclerView = view.findViewById(R.id.raceResultsRecyclerView)


        // Configura il LayoutManager per il RecyclerView (LinearLayoutManager in questo caso)
        val layoutManager = LinearLayoutManager(requireContext())
        rv.layoutManager = layoutManager




        viewModel.sprintResults.observe(viewLifecycleOwner, Observer { sprintResults ->

            if (sprintResults.size == 0) {
                sprintButton.visibility = View.GONE
                sprintBlackLine.visibility = View.GONE
                sprintResultsText.visibility = View.GONE
            } else {
                sprintButton.visibility = View.VISIBLE
//                sprintBlackLine.visibility = View.VISIBLE
                sprintResultsText.visibility = View.VISIBLE
            }
//            Log.d("RACE RESULTS FRAGMENT", raceResults.toString())

            val adapter = SprintResultsAdapter(requireContext(), sprintResults)
            rv.adapter = adapter
        })

        viewModel.getsSprintResults(raceYear, args.seasonRound)




        viewModel.raceResults.observe(viewLifecycleOwner, Observer { raceResults ->

            if (raceResults.size == 0) {
                raceResultsNotAvailableOverlay.visibility = View.VISIBLE
                raceResultsNotAvailableTextView.visibility = View.VISIBLE
            } else {
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



        sprintButton.setOnClickListener {
            viewModel.getsSprintResults(raceYear, args.seasonRound)
            sprintButton.setImageResource(R.drawable.race_results_header)
            raceButton.setImageResource(R.drawable.race_results_header_grey)
            sprintBlackLine.visibility = View.VISIBLE
            raceBlackLine.visibility = View.GONE
        }
        raceButton.setOnClickListener {
            viewModel.getRaceResults(raceYear, args.seasonRound)
            raceButton.setImageResource(R.drawable.race_results_header)
            sprintButton.setImageResource(R.drawable.race_results_header_grey)
            sprintBlackLine.visibility = View.GONE
            raceBlackLine.visibility = View.VISIBLE
//            raceResultsText.setTextColor(Colors(Color.BLACK))

        }









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