package com.dicoding.picodiploma.loginwithanimation.view

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.remote.pref.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityMapsBinding
import com.dicoding.picodiploma.loginwithanimation.data.Result
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val viewModel by viewModels<MapsViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private fun createMarker(data: ListStoryItem): MarkerOptions {
        val latLng = LatLng(data.lat ?: 6.1750, data.lon ?: 106.8283)
        return MarkerOptions()
            .position(latLng)
            .title(data.name)
            .snippet(data.description)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getStoriesAndAddMarkers()
        setMapStyle()
    }

    private fun setMapStyle() {
        try {
            val success =
                mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style))
            if (!success) {
                Log.e(TAG, "Style parsing failed.")
            }
        } catch (exception: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", exception)
        }
    }

    private fun getStoriesAndAddMarkers() {
        viewModel.getStoriesWithLocation().observe(this) { result ->
            when (result) {
                is Result.Loading -> {}
                is Result.Success -> {
                    setupMapMarkers(result.data.listStory)
                }
                is Result.Error -> {
                    Log.e(TAG, "Error fetching stories: ${result.error}")
                }
            }
        }
    }

    private fun setupMapMarkers(stories: List<ListStoryItem>) {
        val defaultLocation = LatLng(6.1750, 106.8283)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))

        stories.forEach { story ->
            mMap.addMarker(createMarker(story))
        }
    }

    companion object {
        private const val TAG = "MapsActivity"
    }

}