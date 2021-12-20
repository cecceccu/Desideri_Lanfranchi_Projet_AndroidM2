package com.example.desideri_lanfranchi_projet_androidm2.model

data class PathPoint (val time: Long,
                      val latitude: Double,
                      val longitude: Double,
                      val baro_altitude: Double,
                      val true_track: Double,
                      val on_ground: Boolean)