package com.programmazionemobile.formula1app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://ergast.com/api/f1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: Service = retrofit.create(Service::class.java)

}
