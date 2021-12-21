package com.example.desideri_lanfranchi_projet_androidm2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desideri_lanfranchi_projet_androidm2.model.DataHolder
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel
import com.example.desideri_lanfranchi_projet_androidm2.services.RequestManager
import com.example.desideri_lanfranchi_projet_androidm2.services.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SharedFlightViewModel : ViewModel() {
    private val selectedFlightLiveData = MutableLiveData<FlightModel>()

    fun getSelectedFlightLiveData(): LiveData<FlightModel> {
        return selectedFlightLiveData
    }



    fun updateSelectedFlight(flight: FlightModel) {
        DataHolder.selectedFlight = flight
        selectedFlightLiveData.value = flight

    }


}