package com.example.desideri_lanfranchi_projet_androidm2.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desideri_lanfranchi_projet_androidm2.model.DataHolder
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel

class FlightListFragmentViewModel : ViewModel() {
    private val flightListLiveData = MutableLiveData<List<FlightModel>>()

    init {
        flightListLiveData.value = DataHolder.flightsList
    }

    fun getFlightListLiveData(): LiveData<List<FlightModel>> {
        return flightListLiveData
    }


}