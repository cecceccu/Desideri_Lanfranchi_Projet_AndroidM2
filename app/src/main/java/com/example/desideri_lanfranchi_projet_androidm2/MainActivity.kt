package com.example.desideri_lanfranchi_projet_androidm2

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.*

lateinit var  viewModel: MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val airportSpinner = findViewById<Spinner>(R.id.airport_spinner)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getAirportNamesListLivedata().observe(this, Observer {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item,
           it
        )

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            findViewById<Spinner>(R.id.airport_spinner).adapter = adapter })

        val fromDateTextView = findViewById<TextView>(R.id.textview_from_date)
        val toDateTextView = findViewById<TextView>(R.id.textview_to_date)

        viewModel.getFromCalendarLiveData().observe(this, Observer {
            fromDateTextView.text = Utils.dateToString(it.time)
            if (it.time>viewModel.getToCalendarLiveData().value!!.time)
                findViewById<TextView>(R.id.textview_to_date).text = Utils.dateToString(it.time)
                viewModel.getToCalendarLiveData().value!!.time = it.time
        })

        viewModel.getToCalendarLiveData().observe(this, Observer {
            toDateTextView.text = Utils.dateToString(it.time)
        })

        fromDateTextView.setOnClickListener {
            showDatePicker(it.id)
        }

        toDateTextView.setOnClickListener {
            showDatePicker(it.id)
        }

        findViewById<Button>(R.id.search_flights).setOnClickListener{
            val airportSelectedIndex = airportSpinner.selectedItemPosition

            val isArrival = findViewById<Switch>(R.id.departure_arrival_switch).isActivated
            viewModel.doSearch(airportSelectedIndex, isArrival)
        }

    }

    private fun showDatePicker(clickedViewId: Int) {
        val calendar: Calendar =
            if (clickedViewId == R.id.textview_from_date) viewModel.getFromCalendarLiveData().value!! else viewModel.getToCalendarLiveData().value!!
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                viewModel.updateCalendar(
                    year,
                    monthOfYear,
                    dayOfMonth,
                    clickedViewId == R.id.textview_from_date
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
        if (clickedViewId == R.id.textview_to_date)
            datePickerDialog.datePicker.minDate = viewModel.getFromCalendarLiveData().value!!.timeInMillis
        datePickerDialog.show()
    }
}