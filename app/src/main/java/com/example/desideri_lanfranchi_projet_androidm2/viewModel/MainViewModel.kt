package com.example.desideri_lanfranchi_projet_androidm2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desideri_lanfranchi_projet_androidm2.model.Airport
import com.example.desideri_lanfranchi_projet_androidm2.model.DataHolder
import com.example.desideri_lanfranchi_projet_androidm2.services.RequestManager
import com.example.desideri_lanfranchi_projet_androidm2.services.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainViewModel: ViewModel() {
    private var airportList: List<Airport> = Utils.generateAirportList()
    private val airportListLiveData = MutableLiveData<List<String>>()

    private val fromCalendarLiveData = MutableLiveData<Calendar>()
    private val toCalendarLiveData = MutableLiveData<Calendar>()

    private val requestStatusLiveData = MutableLiveData<Int>()


    fun getAirportList(): List<Airport>
    {
        return airportList
    }
    fun getAirportNamesListLivedata(): LiveData<List<String>>
    {
        return airportListLiveData
    }


    fun getFromCalendarLiveData(): LiveData<Calendar>
    {
        return fromCalendarLiveData
    }

    fun getToCalendarLiveData(): LiveData<Calendar>
    {
        return toCalendarLiveData
    }

    fun getRequestStatusLiveData(): MutableLiveData<Int>
    {
        return requestStatusLiveData
    }

    init
    {
        val airportNamesList = ArrayList<String>()
        for (airport in airportList)
        {
            airportNamesList.add(airport.getFormattedName())
        }
        airportListLiveData.value = airportNamesList

        fromCalendarLiveData.value = Calendar.getInstance()
        toCalendarLiveData.value = Calendar.getInstance()
        requestStatusLiveData.value = 0
    }

    fun updateCalendar(year: Int, month: Int, day: Int, isFromCalendar: Boolean)
    {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        if (isFromCalendar)
            fromCalendarLiveData.value = calendar
        else
            toCalendarLiveData.value = calendar
    }

    fun doSearch(airportSelectedIndex: Int, isArrival: Boolean)
    {
        var url = if (isArrival) "https://opensky-network.org/api/flights/arrival" else "https://opensky-network.org/api/flights/departure"

        val airportIcao = airportList[airportSelectedIndex].icao

        val begin = fromCalendarLiveData.value!!.timeInMillis/1000
        val end = toCalendarLiveData.value!!.timeInMillis/1000

        url += "?airport=${airportIcao}&begin=${begin}&end=${end}"

        Log.i("URL", url)

        viewModelScope.launch {
            requestStatusLiveData.value = 1 // 1 means pending
            withContext(Dispatchers.IO){
                val result = RequestManager.getSuspended(url, HashMap())
            //CACA : requestStatusLiveData to trigger navigation is limit limit
                if (result != null){
                    Log.i("RESULT", result)
                    val flightsList = Utils.convertFlightDataIntoList(result)
                    DataHolder.flightsList = flightsList
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