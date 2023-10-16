package com.programmazionemobile.formula1app

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.programmazionemobile.formula1app.model.CircuitInfoViewModel
import kotlin.math.absoluteValue

class RaceFragment: Fragment() {

    private val args: CircuitInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val thisView = inflater.inflate(R.layout.fragment_race_info, container, false)

        val infoCircuitBundle = Bundle()

        infoCircuitBundle.putString("circuitName", args.circuitName)
        infoCircuitBundle.putString("circuitID", args.circuitID)

        thisView.findViewById<ImageView>(R.id.infoCircuitoCard).setOnClickListener{
                view-> view.findNavController().navigate(R.id.action_raceFragment2_to_circuitInfoFragment, infoCircuitBundle)
        }


        val raceResultsBundle = Bundle()

        raceResultsBundle.putString("circuitName", args.circuitName)
        raceResultsBundle.putString("circuitID", args.circuitID)
        raceResultsBundle.putString("raceDate", args.raceDate)
        raceResultsBundle.putString("raceName", args.raceName)
        raceResultsBundle.putString("seasonRound", args.calendarRound)

        thisView.findViewById<ImageView>(R.id.raceCard).setOnClickListener{
                view-> view.findNavController().navigate(R.id.action_raceFragment2_to_raceResultsFragment, raceResultsBundle)
        }
        thisView.findViewById<ImageView>(R.id.qualiCard).setOnClickListener{
                view-> view.findNavController().navigate(R.id.action_raceFragment2_to_qualifyingFragment, raceResultsBundle)
        }

        return thisView
    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val auth = FirebaseAuth.getInstance()

        super.onViewCreated(view, savedInstanceState)
        val raceName = view.findViewById<TextView>(R.id.nomeGara)
        val raceDate = view.findViewById<TextView>(R.id.dataGara)
        val startWeekEnd = view.findViewById<TextView>(R.id.weekendGaraInizio)
        val endWeekEnd = view.findViewById<TextView>(R.id.weekendGaraFine)
        val meseWeekEnd = view.findViewById<TextView>(R.id.weekendGaraMese)
        val raceDateRaceCard = view.findViewById<TextView>(R.id.raceDay)
        val qualiDateRaceCard = view.findViewById<TextView>(R.id.qualiDay)
        val giorniMancanti = view.findViewById<TextView>(R.id.leftDays)
        val oreMancanti = view.findViewById<TextView>(R.id.leftHours)
        val minutiMancanti = view.findViewById<TextView>(R.id.leftMins)
        val raceHourRaceCard = view.findViewById<TextView>(R.id.orarioGara)
        val qualiHourRaceCard = view.findViewById<TextView>(R.id.orarioQuali)
        val liveChatCard = view.findViewById<ImageView>(R.id.liveChatCard)

        val raceInfoBackArrow = view.findViewById<ImageView>(R.id.raceInfoBackArrow)
        raceInfoBackArrow.setOnClickListener{
            findNavController().popBackStack()
        }

        raceName.text = args.raceName
        raceDate.text = DateConverter.convertDateInfo(args.raceDate).uppercase()
        startWeekEnd.text = DateConverter.convertDateGiorno(args.firstDate)
        raceDateRaceCard.text = DateConverter.convertDate(args.raceDate).uppercase()
        qualiDateRaceCard.text = DateConverter.convertDate(args.qualiDate).uppercase()
        endWeekEnd.text = DateConverter.convertDateGiorno(args.raceDate)
        meseWeekEnd.text = DateConverter.convertDateMese(args.raceDate).uppercase()
        giorniMancanti.text = DateConverter.giorniRimanenti(args.raceDate)

        if (args.raceHour == "Dati non disponibili") {
            oreMancanti.text = "00"
            minutiMancanti.text = "00"
            raceHourRaceCard.text = args.raceHour
        } else {
            oreMancanti.text = DateConverter.oreRimanenti(args.raceDate, args.raceHour)
            minutiMancanti.text = DateConverter.minutiRimanenti(args.raceDate, args.raceHour)
            raceHourRaceCard.text =
                DateConverter.convertUTCtoLocalTime2(args.raceHour, args.raceDate)
        }
        if (args.qualiHour == "Dati non disponibili")
            qualiHourRaceCard.text = args.qualiHour
        else
            qualiHourRaceCard.text =
                DateConverter.convertUTCtoLocalTime2(args.qualiHour, args.raceDate)

        val controllo = auth.currentUser?.uid

        if (controllo == null) {
            liveChatCard.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Per entrare nella Chat effettuare il login",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else if (args.raceDate != "Dati non disponibili" && args.raceHour != "Dati non disponibili") {
            if (DateConverter.calcolaMinutiMancanti(
                    dataDesiderata = args.raceDate,
                    orarioString = args.raceHour
                ) > 1440
            ) {
                liveChatCard.setOnClickListener {
                    Toast.makeText(
                        requireContext(),
                        "La Chat si aprirà 24 ore prima dell'inizio del Gran Premio",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else
                liveChatCard.setOnClickListener {

                    val infoCircuitBundle = Bundle()
                    infoCircuitBundle.putString("circuitID", args.circuitID)
                    infoCircuitBundle.putString("circuitName", args.circuitName)

                    view.findNavController().navigate(R.id.action_raceFragment2_to_liveChatFragment, infoCircuitBundle)
                }
        } else {
            liveChatCard.setOnClickListener {
                Toast.makeText(requireContext(),
                    "La Chat relativa a questo Gran Premio è stata chiusa",
                    Toast.LENGTH_LONG).show()
            }
        }

        /*else if (DateConverter.calcolaMinutiMancanti(dataDesiderata = args.raceDate, orarioString = args.raceHour) < -1440)
            liveChatCard.setOnClickListener{
                Toast.makeText(requireContext(), "La Chat si chiude dopo 24 ore dalla fine del Gran Premio", Toast.LENGTH_SHORT).show()
            }*/
    }
}