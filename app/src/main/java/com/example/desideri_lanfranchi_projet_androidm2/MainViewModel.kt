package com.example.desideri_lanfranchi_projet_androidm2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel: ViewModel() {
    private val airportListLiveData = MutableLiveData<List<String>>()

    private val fromCalendarLiveData = MutableLiveData<Calendar>()

    fun getAirportListNamesLivedata(): LiveData<List<String>>
    {
        return airportListLiveData
    }

    fun getFromCalendarLiveData(): LiveData<Calendar>
    {
        return fromCalendarLiveData
    }

    init
    {
        val airportList = Utils.generateAirportList()
        val airportNamesList = ArrayList<String>()
        for (airport in airportList)
        {
            airportNamesList.add(airport.getFormattedName())
        }
        airportListLiveData.value = airportNamesList

        fromCalendarLiveData.value = Calendar.getInstance()
    }

    fun updateFromCalendar(year: Int, month: Int, day: Int)
    {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        fromCalendarLiveData.value = calendar
    }
}