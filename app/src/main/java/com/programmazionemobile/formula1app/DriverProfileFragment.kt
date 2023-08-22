package com.programmazionemobile.formula1app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.navArgs

class DriverProfileFragment : Fragment() {
    private val args: DriverProfileFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    /*        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val driverID = args.DriverID
        val driverName = view.findViewById<TextView>(R.id.DriverProfileName)
        val driverFamilyName = view.findViewById<TextView>(R.id.DriverProfileSurname)
        val driverCountry = view.findViewById<TextView>(R.id.DriverProfileCountry)
        val driverBirth = view.findViewById<TextView>(R.id.DriverProfileBirthDate)
        val driverTeam = view.findViewById<TextView>(R.id.DriverProfileTeam)
        val driverNumber = view.findViewById<TextView>(R.id.DriverProfileNumber)
        val driverImage = view.findViewById<ImageView>(R.id.DriverProfileImage)
        val driverSeasonPosition = view.findViewById<TextView>(R.id.driverProfileSeasonPosition)
        val driverSeasonPoints = view.findViewById<TextView>(R.id.driverProfileSeasonPoints)
//        val driverCountry = view.findViewById<TextView>(R.id.DriverProfileCountry)
//        Log.d("asdasdasdasdasdasdasdasdasdasdasdasdasdasd", driverData.url)
        driverName.text = args.DriverName
        driverFamilyName.text = args.DriverFamilyName
        driverCountry.text = args.DriverCountry
        driverNumber.text = args.DriverNumber
        driverBirth.text = args.DriverBirth
        driverTeam.text = args.DriverTeam
        driverSeasonPosition.text = args.DriverCurrentSeasonPosition
        driverSeasonPoints.text = args.DriverPoints


        val drawableResId = requireContext().resources.getIdentifier(driverID, "drawable",  requireContext().packageName)
        if (drawableResId != 0) {
            // Se l'ID è diverso da 0, il drawable è stato trovato
            driverImage.setImageResource(drawableResId) // Imposta il drawable dell'ImageView
        } else {
            // Il drawable non è stato trovato
        }


    }

}