package com.programmazionemobile.formula1app.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.programmazionemobile.formula1app.R

class LoginFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun singInGoogle(){
        //esegue il logout perché non posso loggarmi se qualcuno è già loggato
        googleSignInClient.signOut().addOnCompleteListener {
        }

        val signIntent = googleSignInClient.signInIntent    //mi loggo
        launcher.launch(signIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults (task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                updateUI(account)
            }
        } else {
            Toast.makeText(requireContext(), task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI (account: GoogleSignInAccount) {
        //prende le credenziali dell'utente loggato
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        //logga l'utente e se riesce allora porta l'utente sul fragment del suo account passandogli i vari dati
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val action = LoginFragmentDirections.actionAccountFragmentToAccountFragment4(
                    displayName = account.givenName ?: "No givenName",
                    familyName = account.familyName ?: "No familyName",
                    email = account.email ?: "No email"
                )
                findNavController().navigate(action)
            } else {
                Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val controllo = auth.currentUser?.email
        if (controllo != null){
            val action = LoginFragmentDirections.actionAccountFragmentToAccountFragment4()
            findNavController().navigate(action)
        }
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.loginButton)?.setOnClickListener{
            singInGoogle()
        }
    }
}