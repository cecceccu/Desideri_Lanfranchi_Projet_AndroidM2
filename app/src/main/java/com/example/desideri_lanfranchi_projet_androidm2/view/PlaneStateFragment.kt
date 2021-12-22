package com.example.desideri_lanfranchi_projet_androidm2.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.desideri_lanfranchi_projet_androidm2.R
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.FlightMapViewModel
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.PlaneStateViewModel
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.SharedFlightViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.CameraUpdate

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class PlaneStateFragment: Fragment(), OnMapReadyCallback {

    private lateinit var v: View

    private lateinit var mapView: MapView
    private lateinit var viewModel: PlaneStateViewModel
    private lateinit var back_to_list_button: Button
    private lateinit var less_detail_button: Button
    private lateinit var flight_history: Button

    private lateinit var speed: TextView
    private lateinit var altitude: TextView
    private lateinit var ascending: TextView
    private var isTablet:Boolean = false

    private var lat:Double = -1000.0
    private var lon:Double = -1000.0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.plane_state_fragment, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mapView = v.findViewById(R.id.mapView2)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        viewModel = ViewModelProvider(this).get(PlaneStateViewModel::class.java)
        back_to_list_button = v.findViewById(R.id.back_to_list_2)
        less_detail_button = v.findViewById(R.id.show_less_button)
        flight_history = v.findViewById(R.id.plane_flight_history_button)

        speed = v.findViewById(R.id.speedValue)
        altitude = v.findViewById(R.id.altitudeValue)
        ascending = v.findViewById(R.id.ascendingValue)

        isTablet = activity?.findViewById<View>(R.id.fragment_map_container) != null

        var selectedAirplaneState = viewModel.getStateLiveData().value


                speed.text = if (selectedAirplaneState?.states?.get(0)?.get(9) != null) selectedAirplaneState?.states?.get(0)?.get(9).toString() + " m/s" else "Unknown"
                altitude.text = if (selectedAirplaneState?.states?.get(0)?.get(7) !=null) selectedAirplaneState?.states?.get(0)?.get(7).toString() + " m" else "Unknown"

                if (selectedAirplaneState?.states?.get(0)?.get(11) == null)
                {
                    ascending.text = "Unknown"
                }
                else
                {
                    if ((selectedAirplaneState?.states?.get(0)?.get(11) as Double) > 0)
                    {
                        ascending.text = "Ascending"
                    }
                    else if ((selectedAirplaneState?.states?.get(0)?.get(11) as Double) < 0)
                    {
                        ascending.text = "Descending"
                    } else
                    {
                        ascending.text = "Stable altitude"
                    }
                }

                if (selectedAirplaneState?.states?.get(0)?.get(6) != null)
                {
                    lat = selectedAirplaneState?.states?.get(0)?.get(6) as Double
                }
                else
                {
                    lat = -1000.0
                }

                if (selectedAirplaneState?.states?.get(0)?.get(5) != null)
                {
                    lon = selectedAirplaneState?.states?.get(0)?.get(5) as Double
                }
                else
                {
                    lat = -1000.0
                }
        less_detail_button.setOnClickListener {
            fragmentManager?.popBackStackImmediate()

        }

        if (!isTablet)
        {
            back_to_list_button.visibility = Button.VISIBLE

            back_to_list_button.setOnClickListener {
                startActivity(Intent(activity, FlightListActivity::class.java))
            }


        }

        flight_history.setOnClickListener {

            viewModel.searchAirplaneFlights()

        }



        val progressBar = v.findViewById<ProgressBar>(R.id.progress_circular_3)

        viewModel.getRequestStatusLiveData().observe(viewLifecycleOwner, {
            if(it==1) //pending
            {
                progressBar.visibility = View.VISIBLE
            }
            else
            {
                progressBar.visibility = View.INVISIBLE
            }
            if(it==400){
                Toast.makeText(activity, "An error has occurred", Toast.LENGTH_SHORT).show()
            }
            else if(it==200)
            {
                startActivity(Intent(activity, AircraftFlightListActivity::class.java))
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {

        googleMap.clear()


        if (lon != -1000.0 && lat != -100.0)
        {
            val coordinate = LatLng(lat, lon)
            val yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 5.0f)
            googleMap.animateCamera(yourLocation)
            googleMap.addMarker(
                MarkerOptions()
                    .position(coordinate))
        }






    }


    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }



}