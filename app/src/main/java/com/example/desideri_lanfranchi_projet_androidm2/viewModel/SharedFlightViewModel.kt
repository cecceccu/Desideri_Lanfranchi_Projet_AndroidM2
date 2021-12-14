package com.example.desideri_lanfranchi_projet_androidm2.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel

/**
 * Created by sergio on 11/10/21
 * All rights reserved GoodBarber
 */
class SharedFlightViewModel : ViewModel() {
    private val selectedFlightLiveData = MutableLiveData<FlightModel>()

    fun getSelectedFlightLiveData(): LiveData<FlightModel> {
        return selectedFlightLiveData
    }

    fun updateSelectedFlight(flight: FlightModel) {
        selectedFlightLiveData.value = flight
    }
}