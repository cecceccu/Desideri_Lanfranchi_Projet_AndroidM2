package com.example.desideri_lanfranchi_projet_androidm2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desideri_lanfranchi_projet_androidm2.model.DataHolder
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel
import com.example.desideri_lanfranchi_projet_androidm2.model.State
import com.example.desideri_lanfranchi_projet_androidm2.model.Track
import com.example.desideri_lanfranchi_projet_androidm2.services.RequestManager
import com.example.desideri_lanfranchi_projet_androidm2.services.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlightMapViewModel : ViewModel() {

    private val flightTrackLiveData = MutableLiveData<Track>()

    private val requestStatusLiveData = MutableLiveData<Int>()

    private val airplaneStateLiveData = MutableLiveData<State>()



    fun getFlightTrackLiveData(): LiveData<Track>
    {
        return flightTrackLiveData
    }

    fun getRequestStatusLiveData(): LiveData<Int> {
        return requestStatusLiveData
    }

    fun getStateLiveData(): LiveData<State>{
        return airplaneStateLiveData
    }


    fun updatePath()
    {
        var selectedFlight = DataHolder.selectedFlight
        var url = "https://opensky-network.org/api/tracks/all"
        val icao24 = selectedFlight!!.icao24

        val lastSeen = selectedFlight!!.lastSeen

        url += "?icao24=${icao24}&time=${lastSeen}"

        Log.i("URL", url)

        viewModelScope.launch {
            requestStatusLiveData.value = 1 // 1 means pending
            withContext(Dispatchers.IO){
                val result = RequestManager.getSuspended(url, HashMap())
                if (result != null){
                    Log.i("RESULT", result)
                    val track = Utils.convertTrackDataIntoObject(result)
                    DataHolder.track = track
                    flightTrackLiveData.postValue(track)
                    requestStatusLiveData.postValue(200)

                }
                else
                {
                    requestStatusLiveData.postValue(400)
                }
            }
        }







    }

    fun showDetails()
    {
        var selectedFlight = DataHolder.selectedFlight
        var url = "https://opensky-network.org/api/states/all"
        val icao24 = selectedFlight!!.icao24


        url += "?icao24=${icao24}&time=0"

        Log.i("URL", url)

        viewModelScope.launch {
            requestStatusLiveData.value = 2 // 2 means pending
            withContext(Dispatchers.IO){
                val result = RequestManager.getSuspended(url, HashMap())
                if (result != null){
                    Log.i("RESULT", result)
                    val state = Utils.convertStateDataIntoObject(result)
                    DataHolder.selectedAirplaneState = state
                    airplaneStateLiveData.postValue(state)

                    if (state.states==null)
                    {
                        requestStatusLiveData.postValue(492)
                    }
                    else
                    {
                        if (state.states[0].size<17)
                        {
                            requestStatusLiveData.postValue(492)
                        }
                        else
                        {
                            requestStatusLiveData.postValue(291)
                        }

                    }
                }
                else
                {
                    requestStatusLiveData.postValue(491)
                }
            }
        }
    }

}