package com.example.desideri_lanfranchi_projet_androidm2.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.desideri_lanfranchi_projet_androidm2.R

class AircraftFlightListActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aircraft_flights)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.aircraft_flights_activity_fragmentContainerView, AircraftFlightListFragment())
            addToBackStack(null)
        }
    }
}