package com.example.desideri_lanfranchi_projet_androidm2.services

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.desideri_lanfranchi_projet_androidm2.R
import com.example.desideri_lanfranchi_projet_androidm2.model.FlightModel
import java.text.SimpleDateFormat
import java.util.*

class FlightCell : RelativeLayout {

    var callSignTextView: TextView? = null
    var fromTextView: TextView? = null
    var toTextView: TextView? = null
    var fromDateTextView: TextView? = null
    var toDateTextView: TextView? = null

    constructor(context: Context?) : super(context) {
        initLayout()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initLayout()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout()
    }

    private fun bindViews() {
        // make the find view by ids for your view
        callSignTextView = findViewById(R.id.flightcell_frag_flight_callsign)
        fromTextView = findViewById(R.id.flightcell_frag_departure_airport)
        toTextView = findViewById(R.id.flightcell_frag_arrival_airport)
        fromDateTextView = findViewById(R.id.flightcell_frag_departure_date)
        toDateTextView = findViewById(R.id.flightcell_frag_arrival_date)
    }

    //CACA: c'est pas bien de traiter de la donnée dans une vue
    fun bindData(flight: FlightModel) {
        //fill your views
        callSignTextView?.text = flight.callsign
        fromTextView?.text = flight.estDepartureAirport
        toTextView?.text = flight.estArrivalAirport
        fromDateTextView?.text = epochToDateConverter(flight.firstSeen)
        toDateTextView?.text = epochToDateConverter(flight.lastSeen)



    }

    private fun initLayout() {
        LayoutInflater.from(context).inflate(R.layout.flightcell_view, this, true)
        bindViews()
    }

    private fun epochToDateConverter(epochTime: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm")
        val s:String = sdf.format(Date(epochTime*1000))
        return s
    }
}