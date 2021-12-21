package com.example.desideri_lanfranchi_projet_androidm2.view

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desideri_lanfranchi_projet_androidm2.R
import com.example.desideri_lanfranchi_projet_androidm2.services.Utils
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.FlightListFragmentViewModel
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.MainViewModel
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.SharedFlightViewModel
import java.util.*


class SearchFragment : Fragment() {

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_search, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val airportSpinner = v.findViewById<Spinner>(R.id.search_frag_airport_spinner)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.getAirportNamesListLivedata().observe(viewLifecycleOwner, Observer {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(), android.R.layout.simple_spinner_item,
                it
            )

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            v.findViewById<Spinner>(R.id.search_frag_airport_spinner).adapter = adapter })

        val fromDateTextView = v.findViewById<TextView>(R.id.search_frag_textview_from_date)
        val toDateTextView = v.findViewById<TextView>(R.id.search_frag_textview_to_date)

        viewModel.getFromCalendarLiveData().observe(viewLifecycleOwner, Observer {
            fromDateTextView.text = Utils.dateToString(it.time)

            if (it.time>viewModel.getToCalendarLiveData().value!!.time)
            {
                v.findViewById<TextView>(R.id.search_frag_textview_to_date).text = Utils.dateToString(it.time)
                viewModel.getToCalendarLiveData().value!!.time = it.time
            }

        })

        viewModel.getToCalendarLiveData().observe(viewLifecycleOwner, Observer {
            toDateTextView.text = Utils.dateToString(it.time)

            if (it.time<viewModel.getFromCalendarLiveData().value!!.time)
            {
                v.findViewById<TextView>(R.id.search_frag_textview_from_date).text = Utils.dateToString(it.time)
                viewModel.getFromCalendarLiveData().value!!.time = it.time
            }
        })


        fromDateTextView.setOnClickListener {
            showDatePicker(it.id)
        }

        toDateTextView.setOnClickListener {
            showDatePicker(it.id)
        }

        v.findViewById<Button>(R.id.search_frag_search_flights).setOnClickListener{
            val airportSelectedIndex = airportSpinner.selectedItemPosition

            val isArrival = v.findViewById<Switch>(R.id.search_frag_departure_arrival_switch).isActivated
            viewModel.doSearch(airportSelectedIndex, isArrival)
        }

        val progressBar = v.findViewById<ProgressBar>(R.id.search_frag_progressBar)
        viewModel.getRequestStatusLiveData().observe(viewLifecycleOwner, Observer{
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
                startActivity(Intent(activity, FlightListActivity::class.java))
            }
        })
    }

    private fun showDatePicker(clickedViewId: Int)
    {
        val calendar: Calendar =
            if (clickedViewId == R.id.search_frag_textview_from_date) viewModel.getFromCalendarLiveData().value!! else viewModel.getToCalendarLiveData().value!!
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                viewModel.updateCalendar(
                    year,
                    monthOfYear,
                    dayOfMonth,
                    clickedViewId == R.id.search_frag_textview_from_date
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
        if (clickedViewId == R.id.search_frag_textview_to_date)
            datePickerDialog.datePicker.minDate = viewModel.getFromCalendarLiveData().value!!.timeInMillis
        datePickerDialog.show()
    }
}