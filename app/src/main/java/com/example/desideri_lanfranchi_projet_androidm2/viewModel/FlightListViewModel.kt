package com.example.desideri_lanfranchi_projet_androidm2.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desideri_lanfranchi_projet_androidm2.model.DataHolder
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel

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