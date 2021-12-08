package com.example.desideri_lanfranchi_projet_androidm2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.FlightListViewModel
import com.example.desideri_lanfranchi_projet_androidm2.R

class FlightListActivity : AppCompatActivity() {

    private lateinit var viewModel: FlightListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_list)

        viewModel = ViewModelProvider(this).get(FlightListViewModel::class.java)

        viewModel.getFlightListLiveData().observe(this, Observer {
            findViewById<TextView>(R.id.flights_list_textview).text = it.toString()
        })
    }
}