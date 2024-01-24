package com.programmazionemobile.formula1app

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)      //inflating del layout della main activity

        //inserisco il navHost per contenere i fragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragContainer) as NavHostFragment

        //creo un navcontroller e lo collego al mio navhost
        val navController = navHostFragment.navController

        //prendo la bottom bar e la collego al nav controller per usarla come strumento di navigazione
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)

        FirebaseApp.initializeApp(this)

    }
}