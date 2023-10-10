package com.programmazionemobile.formula1app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.programmazionemobile.formula1app.model.DriverProfileViewModel

class DriverProfileFragment : Fragment() {

    private lateinit var viewModel: DriverProfileViewModel
    private val args: DriverProfileFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_driver_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val driverID = args.DriverID
        val selectedSeason = view.findViewById<TextView>(R.id.DriverProfileSelectedSeason)

        val driverName = view.findViewById<TextView>(R.id.DriverProfileName)
        val driverFamilyName = view.findViewById<TextView>(R.id.DriverProfileSurname)
        val driverCountry = view.findViewById<TextView>(R.id.DriverProfileCountry)
        val driverBirth = view.findViewById<TextView>(R.id.DriverProfileBirthDate)
        val driverTeam = view.findViewById<TextView>(R.id.DriverProfileTeam)
        val driverNumber = view.findViewById<TextView>(R.id.DriverProfileNumber)
        val driverImage = view.findViewById<ImageView>(R.id.DriverProfileImage)
        val driverSeasonPosition = view.findViewById<TextView>(R.id.seasonPosition)
        val driverSeasonPoints = view.findViewById<TextView>(R.id.seasonPoints)
        val driverSeasonPodiums = view.findViewById<TextView>(R.id.seasonPodiums)
        val driverSeasonWins = view.findViewById<TextView>(R.id.SeasonWins)

        val driverCareerPoints = view.findViewById<TextView>(R.id.careerPoints)
        val driverCareerTitles = view.findViewById<TextView>(R.id.careerTitles)
        val driverCareerPoles = view.findViewById<TextView>(R.id.careerPoles)
        val driverCareerWins = view.findViewById<TextView>(R.id.careerWins)
        val driverCareerPodiums = view.findViewById<TextView>(R.id.careerPodiums)
        val driverCareerSeasons = view.findViewById<TextView>(R.id.careerSeasons)
        val driverCareerRaces = view.findViewById<TextView>(R.id.careerRaces)


        val driverProfileBackArrow = view.findViewById<ImageView>(R.id.driverProfileBackArrow)

        driverProfileBackArrow.setOnClickListener{
            findNavController().popBackStack()
        }

        Log.d("YEAR SELECTED DRIVER PROFILE", "${ args.SelectedYearSpinner }")
        selectedSeason.text = args.SelectedYearSpinner + " SEASON"
        driverName.text = args.DriverName
        driverFamilyName.text = args.DriverFamilyName
        driverCountry.text = args.DriverCountry
        if (args.DriverNumber != null && args.DriverNumber != "noDriverNumber") {
            driverNumber.text = args.DriverNumber
        } else {
            driverNumber.text = ""

        }
        driverBirth.text = args.DriverBirth
        driverTeam.text = args.DriverTeam
        driverSeasonPosition.text = args.DriverCurrentSeasonPosition
        driverSeasonPoints.text = args.DriverPoints

        val drawableResId = requireContext().resources.getIdentifier(
            driverID,
            "drawable",
            requireContext().packageName
        )
        if (drawableResId != 0) {
            // Se l'ID è diverso da 0, il drawable è stato trovato
            driverImage.setImageResource(drawableResId) // Imposta il drawable dell'ImageView
        } else {
            // Il drawable non è stato trovato, imposto immagine di default
            driverImage.setImageResource(R.drawable.nodriverpic)
        }


        viewModel = ViewModelProvider(this).get(DriverProfileViewModel::class.java)

        viewModel.driverData.observe(viewLifecycleOwner, Observer { driverData ->
            driverCareerPoles.text = driverData.get("careerPoles") ?: "-"
            driverCareerWins.text = driverData.get("careerWins") ?: "-"
            driverSeasonWins.text = driverData.get("seasonWins") ?: "-"
            driverCareerPodiums.text = driverData.get("careerPodiums") ?: "-"
            driverSeasonPodiums.text = driverData.get("seasonPodiums") ?: "-"
            driverCareerSeasons.text = driverData.get("careerSeasons") ?: "-"
            driverCareerRaces.text = driverData.get("careerRaces") ?: "-"

        })
        viewModel.getDriverStats(driverID, args.SelectedYearSpinner)

        viewModel.driverStandingsDataPoints.observe(viewLifecycleOwner, Observer { driverStandingsDataPoints ->
            var totalPoints = args.DriverPoints.toFloat() + driverStandingsDataPoints.get("careerPoints")!!.toFloat()

            driverCareerTitles.text = driverStandingsDataPoints.get("careerTitles")
            driverCareerPoints.text = totalPoints.toString()

        })
        viewModel.getDriverPointsTitles(driverID)



        viewModel.isInternetConnected.observe(viewLifecycleOwner) { isInternetConnected ->
                if (isInternetConnected == false) {

                    val alertDialogBuilder = AlertDialog.Builder(requireContext())

                    alertDialogBuilder.setTitle("Can't connect to database.")

                    alertDialogBuilder.setMessage("Check your internet connection!")

                    alertDialogBuilder.setPositiveButton("Try again") { dialog, which ->

                        viewModel.getDriverStats(driverID, args.SelectedYearSpinner)
                        viewModel.getDriverPointsTitles(driverID)

                        dialog.dismiss()
                    }

                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
            }

    }
}