package com.example.desideri_lanfranchi_projet_androidm2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.FlightListViewModel
import com.example.desideri_lanfranchi_projet_androidm2.R
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.SharedFlightViewModel

class FlightListActivity : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedFlightViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_list)

        val isTablet = findViewById<View>(R.id.fragment_map_container) != null
        Log.i("Tablet", "isTablet $isTablet")

        sharedViewModel = ViewModelProvider(this).get(SharedFlightViewModel::class.java)
        sharedViewModel.getSelectedFlightLiveData().observe(this, Observer {
            if (!isTablet) {
                // Remplacer le fragment
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<FlightMapFragment>(R.id.fragment_list_container)
                    addToBackStack(null)
                }
            }
        })


        val listFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_list_container) as FlightListFragment
    }
}