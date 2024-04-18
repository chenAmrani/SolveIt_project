package com.example.solveitproject

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMapsApiActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps_api)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)




        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true

    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.uiSettings.isZoomControlsEnabled = true
        googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE


        // Add a marker for Algorithmim
        val location1 = LatLng(32.109333, 34.855499)
        googleMap.addMarker(MarkerOptions().position(location1).title("Israel Farkash teacher of algo, Tel Aviv"))

        // Add a marker for Rishon LeZion
        val location2 = LatLng(31.9585, 34.8101)
        googleMap.addMarker(MarkerOptions().position(location2).title("Moshe Cohen teacher of C/C++,jAVA,Rishon LeZion"))

        // Add a marker for Jerusalem
        val location3 = LatLng(31.7683, 35.2137)
        googleMap.addMarker(MarkerOptions().position(location3).title("Yuval Cohen teacher of data structure, Jerusalem"))

        // Add a marker for Modiin
        val location4 = LatLng(31.8904, 35.0057)
        googleMap.addMarker(MarkerOptions().position(location4).title("Chen Amrani teacher of Java,Modiin"))

        // Add a marker for Ramat Gan
        val location5 = LatLng(32.0853, 34.8119)
        googleMap.addMarker(MarkerOptions().position(location5).title("Ori Farkash teacher of C++,Ramat Gan"))

        // Add a marker for Petach Tikva
        val location6 = LatLng(32.0869, 34.8878)
        googleMap.addMarker(MarkerOptions().position(location6).title("Oren ShemTov teacher of Python,Petach Tikva"))

        // Add a marker for Haifa
        val location7 = LatLng(32.8054, 34.9721)
        googleMap.addMarker(MarkerOptions().position(location7).title("Roy Ben Moshe teacher of Analiza, Haifa"))

        // Add a marker for Ness Ziona
        val location8 = LatLng(31.9503, 34.7881)
        googleMap.addMarker(MarkerOptions().position(location8).title("Israel Israeli teacher of algo,Ness Ziona"))

        // Add a marker for Holon
        val location9 = LatLng(32.0097, 34.7746)
        googleMap.addMarker(MarkerOptions().position(location9).title("Israel Farkash teacher of Computational models ,Holon"))

        // Add a marker for Rosh HaAyin
        val location10 = LatLng(32.0649, 34.9395)
        googleMap.addMarker(MarkerOptions().position(location10).title("Israel Farkash teacher of Kotlin , Rosh HaAyin"))

        // Add a marker for Beer Yaakov
        val location11 = LatLng(31.9167, 34.7833)
        googleMap.addMarker(MarkerOptions().position(location11).title("Israel Farkash teacher of C# ,Beer Yaakov"))

        // Move the camera to the first marker
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location2, 12f))

    }
}