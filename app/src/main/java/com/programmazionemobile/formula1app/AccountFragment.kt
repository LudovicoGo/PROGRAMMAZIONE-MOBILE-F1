package com.programmazionemobile.formula1app

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class AccountFragment: Fragment() {

    private lateinit var auth : FirebaseAuth
    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate (savedInstanceState)
    }

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()
        val view = inflater.inflate(R.layout.fragment_account_logged, container, false)

        val nomeUtenteText = view.findViewById<TextView>(R.id.nomeUtente)
        val nomeText = view.findViewById<TextView>(R.id.nome)
        val cognomeText = view.findViewById<TextView>(R.id.cognome)
        val emailText = view.findViewById<TextView>(R.id.email)
        val propic = view.findViewById<ImageView>(R.id.profilePic)

        propic.load(auth.currentUser?.photoUrl){
            size(400)
            transformations(RoundedCornersTransformation(1000f))
        }

        view.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            auth.signOut()
            val action = AccountFragmentDirections.actionAccountFragment4ToAccountFragment()
            findNavController().navigate(action)
        }

        println(auth.currentUser?.displayName)

        val partiNomeCognome = auth.currentUser?.displayName?.split(" ")?.toMutableList()

        val editTextNewUsername = view.findViewById<EditText>(R.id.nomeUtente)
        val saveButton = view.findViewById<Button>(R.id.saveButton) // Se hai un pulsante per salvare

        if (partiNomeCognome != null) {
            if (partiNomeCognome.size == 2) {
                val nome = partiNomeCognome[0]
                val cognome = partiNomeCognome[1]

                nomeText.text = nome
                cognomeText.text = cognome
            }
            if (partiNomeCognome.size == 3) {
                val nome = partiNomeCognome[0]
                val cognome = partiNomeCognome[1]
                val username = partiNomeCognome[2]

                nomeText.text = nome
                cognomeText.text = cognome
                nomeUtenteText.text = username
            }
        }

        saveButton.setOnClickListener {
            val newUsername = editTextNewUsername.text.toString()

            // Verifica che il nuovo nome utente sia valido (unico, lunghezza, caratteri consentiti, ecc.)

            // Aggiorna il nome utente nell'oggetto Firebase User
            val user = auth.currentUser
            user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName((partiNomeCognome?.get(0)) + " " + (partiNomeCognome?.get(1)) + " " + newUsername).build())
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Nome utente aggiornato con successo
                        // Puoi anche aggiornare il valore in tempo reale nel tuo database Firebase se necessario
                        val updatedUsername = user.displayName
                        // Aggiorna il nome utente visualizzato nell'EditText
                        editTextNewUsername.setText(updatedUsername)
                        nomeUtenteText.text = newUsername
                    } else {
                        // Gestisci l'errore
                    }
                }
        }
        emailText.text = auth.currentUser?.email

        return view
    }
}