package com.example.desideri_lanfranchi_projet_androidm2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FlightListViewModel: ViewModel() {

    private val flightListLiveData = MutableLiveData<List<FlightModel>>()

    fun getFlightListLiveData(): LiveData<List<FlightModel>>
    {
        return flightListLiveData
    }

    init
    {
       flightListLiveData.value = DataHolder.flightsList
    }

}