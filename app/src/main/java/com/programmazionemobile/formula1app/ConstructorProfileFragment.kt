package com.programmazionemobile.formula1app

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.programmazionemobile.formula1app.adapter.ConstructorProfileDriversAdapter

class ConstructorProfileFragment : Fragment() {




/*    companion object {
        fun newInstance() = ConstructorProfileFragment()
    }*/

    private val args: ConstructorProfileFragmentArgs by navArgs()

    private lateinit var viewModel: ConstructorProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_constructor_profile, container, false)
        return view
    }








    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val constructorID = args.constructorID
        val selectedSeason = view.findViewById<TextView>(R.id.ConstructorProfileSelectedSeason)

        val constructorName = view.findViewById<TextView>(R.id.ConstructorProfileName)
        val constructorNationality = view.findViewById<TextView>(R.id.ConstructorProfileCountry)
        val constructorImageBackground = view.findViewById<ImageView>(R.id.ConstructorProfileImageBackground)
        val constructorImage = view.findViewById<ImageView>(R.id.ConstructorProfileImage)
        val constructorSeasonPosition = view.findViewById<TextView>(R.id.seasonPosition)
        val constructorSeasonPoints = view.findViewById<TextView>(R.id.seasonPoints)
        val constructorSeasonPodiums = view.findViewById<TextView>(R.id.seasonPodiums)
        val constructorSeasonWins = view.findViewById<TextView>(R.id.SeasonWins)

        val constructorHistoryTitles = view.findViewById<TextView>(R.id.careerTitles)
        val constructorHistoryPoles = view.findViewById<TextView>(R.id.careerPoles)
        val constructorHistoryWins = view.findViewById<TextView>(R.id.careerWins)
        val constructorHistoryPodiums = view.findViewById<TextView>(R.id.careerPodiums)
        val constructorHistorySeasons = view.findViewById<TextView>(R.id.careerSeasons)
        val constructorHistoryRaces = view.findViewById<TextView>(R.id.careerRaces)

        val constructorProfileSelectedSeason = view.findViewById<TextView>(R.id.ConstructorProfileSelectedSeason)
        val constructorProfileSelectedSeasonDrivers = view.findViewById<TextView>(R.id.constructorProfileSelectedSeasonDrivers)

        constructorName.text = args.constructorName
        constructorNationality.text = args.constructorNationality
        constructorSeasonPosition.text = args.constructorSeasonPosition
        constructorSeasonPoints.text = args.constructorSeasonPoints
        constructorSeasonWins.text = args.constructorSeasonWins

        constructorProfileSelectedSeason.text = args.selectedSpinnerYear + " SEASON"
        constructorProfileSelectedSeasonDrivers.text = args.selectedSpinnerYear + " DRIVERS"

        val drawableResId = requireContext().resources.getIdentifier(
            constructorID+"_logo",
            "drawable",
            requireContext().packageName
        )
        if (drawableResId != 0) {
            //se l'ID è diverso da 0, il drawable è stato trovato
            constructorImage.visibility = View.VISIBLE
            constructorImageBackground.visibility = View.VISIBLE
            constructorImage.setImageResource(drawableResId) //imposta il drawable dell'ImageView
        } else {
            //il drawable non è stato trovato, imposto immagine di default
            constructorImageBackground.visibility = View.GONE
            constructorImage.visibility = View.GONE
            constructorImage.setImageResource(R.drawable.nodriverpic)
        }







        viewModel = ViewModelProvider(this).get(ConstructorProfileViewModel::class.java)

        val listView = view.findViewById<ListView>(R.id.constructorProfileDriversListView)


        viewModel.constructorDrivers.observe(viewLifecycleOwner, Observer { constructorDrivers ->

            val layoutParams = listView.layoutParams
            layoutParams.height = (122*constructorDrivers.size * resources.displayMetrics.density).toInt() // Sostituisci "nuovaAltezza" con l'altezza desiderata in pixel
            listView.layoutParams = layoutParams

            val adapter = ConstructorProfileDriversAdapter(requireContext(), constructorDrivers)
            listView.adapter = adapter
        })

        viewModel.getConstructorDrivers(args.constructorID, args.selectedSpinnerYear)

        viewModel.constructorData.observe(viewLifecycleOwner, Observer { constructorData ->
            constructorHistoryPoles.text = constructorData.get("historyPoles") ?: "-"
            constructorHistoryWins.text = constructorData.get("historyWins") ?: "-"
            constructorSeasonWins.text = constructorData.get("seasonWins") ?: "-"
            constructorHistoryPodiums.text = constructorData.get("historyPodiums") ?: "-"
            constructorSeasonPodiums.text = constructorData.get("seasonPodiums") ?: "-"
            constructorHistorySeasons.text = constructorData.get("historySeasons") ?: "-"
            constructorHistoryRaces.text = constructorData.get("historyRaces") ?: "-"

        })
        viewModel.getConstructorStats(constructorID, args.selectedSpinnerYear,  "0")

        viewModel.constructorTitles.observe(viewLifecycleOwner, Observer { constructorTitles ->
            constructorHistoryTitles.text = constructorTitles
        })
        viewModel.getConstructorTitles(constructorID)

        viewModel.isInternetConnected.observe(viewLifecycleOwner) { isInternetConnected ->
            if (!isInternetConnected) {
                val toastMessage = "Can't load stats. Check your internet connection!"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(context, toastMessage, duration)
                viewModel.setInternetConnectionStatus(true)
                toast.show()
            }
        }

    }

}