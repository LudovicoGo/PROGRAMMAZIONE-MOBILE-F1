package com.programmazionemobile.formula1app.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RaceViewModel : ViewModel() {
    val oreMancantiLiveData = MutableLiveData<String>()
    val minutiMancantiLiveData = MutableLiveData<String>()

    fun updateOreMancanti(oreMancanti: String) {
        oreMancantiLiveData.value = oreMancanti
    }

    fun updateMinutiMancanti(minutiMancanti: String) {
        minutiMancantiLiveData.value = minutiMancanti
    }
}
