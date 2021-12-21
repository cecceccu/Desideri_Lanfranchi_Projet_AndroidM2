package com.example.desideri_lanfranchi_projet_androidm2.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desideri_lanfranchi_projet_androidm2.model.DataHolder
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel

class FlightListFragmentViewModel : ViewModel() {
    private val flightListLiveData = MutableLiveData<List<FlightModel>>()
    private val selectedFlightLiveData = MutableLiveData<FlightModel>()

    init {
        flightListLiveData.value = DataHolder.flightsList
    }

    fun getFlightListLiveData(): LiveData<List<FlightModel>> {
        return flightListLiveData
    }

    fun updateSelectedFlight(selectedFlight: FlightModel)
    {
        selectedFlightLiveData.value = selectedFlight
    }

    fun getSelectedFlightLiveData(): LiveData<FlightModel>
    {
        return selectedFlightLiveData
    }


}