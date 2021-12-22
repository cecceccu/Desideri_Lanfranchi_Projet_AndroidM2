package com.example.desideri_lanfranchi_projet_androidm2.view

import FlightsRecyclerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desideri_lanfranchi_projet_androidm2.R
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.AircraftFlightListViewModel
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.FlightListFragmentViewModel
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.SharedFlightViewModel
import android.util.DisplayMetrics





class AircraftFlightListFragment: Fragment(), FlightsRecyclerAdapter.OnItemClickListener {

    private lateinit var v:View

    private lateinit var viewModel:AircraftFlightListViewModel

    private lateinit var back_to_search_button: Button
    private lateinit var back_to_details_button: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.aircraft_flights_fragment, container, false)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AircraftFlightListViewModel::class.java)
        viewModel.getAircraftFlightsLiveData().observe(viewLifecycleOwner, Observer {
            val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView_container)
            val adapter = FlightsRecyclerAdapter(it, this)

            val layoutManager: GridLayoutManager

            val displayMetrics = DisplayMetrics()
            activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
            val density = displayMetrics.density
            val dpWidth = displayMetrics.widthPixels/density
            val isTablet = dpWidth >= 500
            if (isTablet) {
                layoutManager = GridLayoutManager(activity, 3)
            } else {
                layoutManager = GridLayoutManager(activity, 1)
            }

            recyclerView?.adapter = adapter
            recyclerView?.layoutManager = layoutManager
            val hDividerItemDecoration = DividerItemDecoration(
                recyclerView?.context,
                DividerItemDecoration.HORIZONTAL
            );
            val vDividerItemDecoration = DividerItemDecoration(
                recyclerView?.context,
                DividerItemDecoration.VERTICAL
            );
            recyclerView?.addItemDecoration(vDividerItemDecoration)

            if (layoutManager.spanCount==3)
            {
                recyclerView?.addItemDecoration(hDividerItemDecoration)
            }


        })

        back_to_search_button =
            v.findViewById<Button>(R.id.aircraft_fligths_frag_back_to_search_button)
        back_to_details_button =
            v.findViewById<Button>(R.id.aircraft_fligths_frag_back_to_details_button)

        back_to_search_button.setOnClickListener {
            startActivity(Intent(activity, FlightListActivity::class.java))
        }

        back_to_details_button.setOnClickListener {
            activity?.finish()
        }
    }

    override fun onItemClicked(selectedFlight: FlightModel) {
        //We do nothing on click, this list is just here for display
    }
}