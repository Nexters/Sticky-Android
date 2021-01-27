package com.nexters.sticky.ui.map

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityMapBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapActivity : BaseActivity<ActivityMapBinding>(), OnMapReadyCallback {
	override val viewModel: MapViewModel by viewModels()

	override val layoutRes = R.layout.activity_map
	override val actionBarLayoutRes = R.layout.actionbar_share_layout
	override val statusBarColorRes = R.color.purple_200
	private lateinit var mMap: GoogleMap
	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.setText("주소주소주소주소주소주소주소주소")
		val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
		mapFragment.getMapAsync(this)
	}

	override fun onMapReady(googleMap: GoogleMap) {
		mMap = googleMap
		val marker = LatLng(37.555449721383795, 126.97578942646165)
		mMap.addMarker(MarkerOptions().position(marker).title("Marker LAB"))
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 18F))
	}
}