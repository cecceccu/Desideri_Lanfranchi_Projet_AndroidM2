package com.example.desideri_lanfranchi_projet_androidm2.view

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.desideri_lanfranchi_projet_androidm2.R
import com.example.desideri_lanfranchi_projet_androidm2.model.DataHolder
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.FlightMapViewModel
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.SharedFlightViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import kotlin.math.log


class FlightMapFragment : Fragment(), OnMapReadyCallback {


    companion object {
        fun newInstance() = FlightMapFragment()
    }

    private lateinit var mapView: MapView
    private lateinit var viewModel: FlightMapViewModel
    private lateinit var sharedViewModel: SharedFlightViewModel
    private lateinit var progressBar: ProgressBar
    private lateinit var back_button: Button
    private lateinit var detail_button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.flight_map_fragment, container, false)
        mapView = v.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        progressBar = v.findViewById<ProgressBar>(R.id.progressBar2)
        back_button = v.findViewById<Button>(R.id.back_to_list_button)
        detail_button = v.findViewById<Button>(R.id.show_detail_button)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val isTablet:Boolean = activity?.findViewById<View>(R.id.fragment_map_container) != null
        Log.i("Tablette", "isTablet $isTablet")

        if (!isTablet)
        {
            back_button.visibility = Button.VISIBLE

            back_button.setOnClickListener {
                fragmentManager?.popBackStackImmediate()
            }
        }

        viewModel = ViewModelProvider(this).get(FlightMapViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedFlightViewModel::class.java)
        sharedViewModel.getSelectedFlightLiveData().observe(viewLifecycleOwner, Observer {
            viewModel.updatePath()
        })

    }


    override fun onMapReady(googleMap: GoogleMap) {


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
                drawPath(googleMap)
            }



        })


    }

    private fun drawPath(googleMap: GoogleMap) {


            googleMap.clear()
            val polylineOptions = PolylineOptions()
            for (point in viewModel.getFlightTrackLiveData().value!!.path)
            {
                var lat = point[1] as Double
                var lon = point[2] as Double
                polylineOptions.add(
                    LatLng(
                        lat,
                        lon
                    )
                )
            }
            polylineOptions.color(Color.RED )
            polylineOptions.width(6f)
            polylineOptions.geodesic(false)
            googleMap.addPolyline(polylineOptions)
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(viewModel.getFlightTrackLiveData().value!!.path[0][1] as Double, viewModel.getFlightTrackLiveData().value!!.path[0][2] as Double))
            )
            googleMap.addMarker(
                MarkerOptions()
                    .position(LatLng(viewModel.getFlightTrackLiveData().value!!.path[viewModel.getFlightTrackLiveData().value!!.path.size - 1][1] as Double,
                        viewModel.getFlightTrackLiveData().value!!.path[viewModel.getFlightTrackLiveData().value!!.path.size - 1][2] as Double))
            )


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