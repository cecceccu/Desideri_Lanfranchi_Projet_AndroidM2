package com.example.desideri_lanfranchi_projet_androidm2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        //Créer l'URL
        var url = if (isArrival) "https://opensky-network.org/api/flights/arrival" else "https://opensky-network.org/api/flights/departure"

        val airportIcao = airportList[airportSelectedIndex].icao

        val begin = fromCalendarLiveData.value!!.timeInMillis/1000
        val end = toCalendarLiveData.value!!.timeInMillis/1000

        url += "?airport=${airportIcao}&begin=${begin}&end=${end}"

        Log.i("URL", url)
        //Faire la requête

        viewModelScope.launch {
            val result = withContext(Dispatchers.IO){
                RequestManager.getSuspended(url, HashMap())
            }
        }
        //Stocker le résultat dans un singleton
    }
}