package com.example.desideri_lanfranchi_projet_androidm2.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.desideri_lanfranchi_projet_androidm2.R
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.FlightMapViewModel
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.SharedFlightViewModel

class FlightMapFragment : Fragment() {

    companion object {
        fun newInstance() = FlightMapFragment()
    }

    private lateinit var viewModel: FlightMapViewModel
    private lateinit var sharedViewModel: SharedFlightViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.flight_map_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val textView = view?.findViewById<TextView>(R.id.textview_callsign)



        viewModel = ViewModelProvider(this).get(FlightMapViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedFlightViewModel::class.java)


        sharedViewModel.getSelectedFlightLiveData().observe(viewLifecycleOwner, Observer {
            textView?.text = it.callsign
        })
    }

}