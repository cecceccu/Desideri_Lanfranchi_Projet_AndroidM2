package com.example.desideri_lanfranchi_projet_androidm2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desideri_lanfranchi_projet_androidm2.model.DataHolder
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel
import com.example.desideri_lanfranchi_projet_androidm2.model.Track
import com.example.desideri_lanfranchi_projet_androidm2.services.RequestManager
import com.example.desideri_lanfranchi_projet_androidm2.services.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlightMapViewModel : ViewModel() {

    private val flightTrackLiveData = MutableLiveData<Track>()

    private val requestStatusLiveData = MutableLiveData<Int>()



    fun getFlightTrackLiveData(): LiveData<Track>
    {
        return flightTrackLiveData
    }

    fun getRequestStatusLiveData(): LiveData<Int> {
        return requestStatusLiveData
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

}