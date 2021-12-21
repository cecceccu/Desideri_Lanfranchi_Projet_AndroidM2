package com.example.desideri_lanfranchi_projet_androidm2.view

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.desideri_lanfranchi_projet_androidm2.R
import com.example.desideri_lanfranchi_projet_androidm2.services.Utils
import com.example.desideri_lanfranchi_projet_androidm2.view.FlightListActivity
import com.example.desideri_lanfranchi_projet_androidm2.viewModel.MainViewModel
import java.util.*

lateinit var  viewModel: MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.main_activity_fragmentContainerView, SearchFragment())
            addToBackStack(null)
        }
    }

}