package com.example.desideri_lanfranchi_projet_androidm2.view

import FlightsRecyclerAdapter
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
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

    private var isTablet:Boolean = false

    private lateinit var viewModel: FlightListFragmentViewModel
    private lateinit var sharedViewModel: SharedFlightViewModel
    private lateinit var selectedFlight: FlightModel

    private lateinit var button: Button
    private lateinit var backButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.flight_list_fragment, container, false)

        button = v.findViewById<Button>(R.id.flight_list_frag_show_button)
        backButton = v.findViewById<Button>(R.id.flight_list_frag_back_button)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isTablet = activity?.findViewById<View>(R.id.fragment_map_container) != null
        viewModel = ViewModelProvider(this).get(FlightListFragmentViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedFlightViewModel::class.java)
        viewModel.getFlightListLiveData().observe(viewLifecycleOwner, Observer {
            val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView_container)
            val adapter = FlightsRecyclerAdapter(it, this)
            val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerView?.adapter = adapter
            recyclerView?.layoutManager = layoutManager
            val dividerItemDecoration = DividerItemDecoration(recyclerView?.context,
                layoutManager.orientation);
            recyclerView?.addItemDecoration(dividerItemDecoration)
        })

        button.setOnClickListener{

            if (viewModel.getSelectedFlightLiveData().value == null)
            {
                Toast.makeText(activity, "Please select a flight first", Toast.LENGTH_SHORT).show()
            }
            else
            {
                if (isTablet)
                {
                    fragmentManager?.popBackStackImmediate()
                }
                sharedViewModel.updateSelectedFlight(viewModel.getSelectedFlightLiveData().value!!)
            }
        }

        backButton.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
        }


    }

    override fun onItemClicked(selectedFlight: FlightModel) {
        viewModel.updateSelectedFlight(selectedFlight)
    }

}