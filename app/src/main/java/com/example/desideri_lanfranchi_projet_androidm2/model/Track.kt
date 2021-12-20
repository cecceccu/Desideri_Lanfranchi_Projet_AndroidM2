package com.example.desideri_lanfranchi_projet_androidm2.model

data class Track (val icao24: String,
                  val callsign: String,
                  val startTime: Double,
                  val endTime: Double,
                  val path: List<List<Any>>)