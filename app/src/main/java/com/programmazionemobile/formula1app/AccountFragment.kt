package com.programmazionemobile.formula1app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class AccountFragment: Fragment() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()
        val view = inflater.inflate(R.layout.fragment_account_logged, container, false)

        val nomeText = view.findViewById<TextView>(R.id.nome)
        val cognomeText = view.findViewById<TextView>(R.id.cognome)
        val emailText = view.findViewById<TextView>(R.id.email)

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            auth.signOut()
            val action = AccountFragmentDirections.actionAccountFragment4ToAccountFragment()
            findNavController().navigate(action)
        }

        val args = AccountFragmentArgs.fromBundle(requireArguments())

        emailText.text = auth.currentUser?.email

        val partiNomeCognome = auth.currentUser?.displayName?.split(" ")

        if (partiNomeCognome != null) {
            if (partiNomeCognome.size == 2) {
                val nome = partiNomeCognome[0]
                val cognome = partiNomeCognome[1]

                nomeText.text = nome
                cognomeText.text = cognome
            }
        }

        return view
    }
}