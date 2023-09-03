package com.programmazionemobile.formula1app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs

class RaceFragment: Fragment() {

    private val args: CircuitInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val thisView = inflater.inflate(R.layout.fragment_race_info, container, false)

        val bundle = Bundle()

        bundle.putString("circuitName", args.circuitName)
        bundle.putString("circuitID", args.circuitID)

        thisView.findViewById<ImageView>(R.id.infoCircuitoCard).setOnClickListener{
                view-> view.findNavController().navigate(R.id.action_raceFragment2_to_circuitInfoFragment, bundle)
        }

        return thisView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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

        raceName.text = args.raceName
        raceDate.text = DateConverter.convertDateInfo(args.raceDate).toUpperCase()
        startWeekEnd.text = DateConverter.convertDateGiorno(args.firstDate)
        raceDateRaceCard.text = DateConverter.convertDate(args.raceDate).toUpperCase()
        qualiDateRaceCard.text = DateConverter.convertDate(args.qualiDate).toUpperCase()
        endWeekEnd.text = DateConverter.convertDateGiorno(args.raceDate)
        meseWeekEnd.text = DateConverter.convertDateMese(args.raceDate).toUpperCase()
        giorniMancanti.text = DateConverter.giorniRimanenti(args.raceDate)
        oreMancanti.text = DateConverter.oreRimanenti(args.raceDate, args.raceHour)
        minutiMancanti.text = DateConverter.minutiRimanenti(args.raceDate, args.raceHour)
        raceHourRaceCard.text = DateConverter.convertUTCtoLocalTime2(args.raceHour, args.raceDate)
        if (args.qualiHour == "Dati non disponibili")
            qualiHourRaceCard.text = args.qualiHour
        else
            qualiHourRaceCard.text = DateConverter.convertUTCtoLocalTime2(args.qualiHour, args.raceDate)

    }
}