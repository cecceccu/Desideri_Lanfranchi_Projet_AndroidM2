package com.example.desideri_lanfranchi_projet_androidm2.view

import FlightsRecyclerAdapter
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desideri_lanfranchi_projet_androidm2.R
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.FlightListFragmentViewModel
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.SharedFlightViewModel

class FlightListFragment : Fragment(), FlightsRecyclerAdapter.OnItemClickListener {

    companion object {
        fun newInstance() = FlightListFragment()
    }

    private lateinit var viewModel: FlightListFragmentViewModel
    private lateinit var sharedViewModel: SharedFlightViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.flight_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FlightListFragmentViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedFlightViewModel::class.java)
        viewModel.getFlightListLiveData().observe(viewLifecycleOwner, Observer {
            val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
            val adapter = FlightsRecyclerAdapter(it, this)
            recyclerView?.adapter = adapter
            recyclerView?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        })
    }

    override fun onItemClicked(selectedFlight: FlightModel) {
        sharedViewModel.updateSelectedFlight(selectedFlight)
    }

}