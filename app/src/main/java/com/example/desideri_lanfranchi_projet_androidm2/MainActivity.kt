package com.example.desideri_lanfranchi_projet_androidm2

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.*

lateinit var  viewModel: MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getAirportListNamesLivedata().observe(this, Observer {
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
        })

        fromDateTextView.setOnClickListener{
            DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener {view, year, monthOfYear, dayOfMonth ->
                viewModel.updateFromCalendar(year, monthOfYear, dayOfMonth)
                }, viewModel.getFromCalendarLiveData().value!!.get(Calendar.YEAR),
                         viewModel.getFromCalendarLiveData().value!!.get(Calendar.MONTH),
                         viewModel.getFromCalendarLiveData().value!!.get(Calendar.DAY_OF_MONTH)).show()
        }

    }
}