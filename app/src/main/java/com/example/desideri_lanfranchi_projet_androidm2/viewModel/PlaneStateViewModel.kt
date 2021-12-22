package com.example.desideri_lanfranchi_projet_androidm2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desideri_lanfranchi_projet_androidm2.model.DataHolder
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel
import com.example.desideri_lanfranchi_projet_androidm2.model.State
import com.example.desideri_lanfranchi_projet_androidm2.services.RequestManager
import com.example.desideri_lanfranchi_projet_androidm2.services.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaneStateViewModel: ViewModel() {

    private val airplaneStateLiveData = MutableLiveData<State>()
    private val requestStatusLiveData = MutableLiveData<Int>()

    fun getStateLiveData(): LiveData<State>
    {
        return airplaneStateLiveData
    }

    fun getRequestStatusLiveData(): LiveData<Int>
    {
        return requestStatusLiveData
    }

    init
    {
        airplaneStateLiveData.value = DataHolder.selectedAirplaneState
    }

    fun searchAirplaneFlights()
    {
        var url = "https://opensky-network.org/api/flights/aircraft"

        val icao24 = DataHolder.selectedFlight?.icao24

        val begin = (System.currentTimeMillis()/1000 - 15*86400) as Long//Getting flights for the 15 last days
        val end = (System.currentTimeMillis()/1000) as Long

        url += "?icao24=${icao24}&begin=${begin}&end=${end}"

        Log.i("URL", url)

        viewModelScope.launch {
            requestStatusLiveData.value = 1 // 1 means pending
            withContext(Dispatchers.IO){
                val result = RequestManager.getSuspended(url, HashMap())
                if (result != null){
                    Log.i("RESULT", result)
                    val flightsList = Utils.convertFlightDataIntoList(result)
                    DataHolder.aircraftFlightsList = flightsList
                    requestStatusLiveData.postValue(200)
                }
                else
                {
                    requestStatusLiveData.postValue(400)
                }
            }
        }


    }
}