package com.example.desideri_lanfranchi_projet_androidm2.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desideri_lanfranchi_projet_androidm2.model.DataHolder
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel

class AircraftFlightListViewModel: ViewModel() {

    private val aircraftFlightsLiveData = MutableLiveData<List<FlightModel>>()

    fun getAircraftFlightsLiveData(): LiveData<List<FlightModel>>
    {
        return aircraftFlightsLiveData
    }

    init
    {
        aircraftFlightsLiveData.value = DataHolder.aircraftFlightsList
    }
}