package com.nexters.sticky.ui.address

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityMapBinding
import com.nexters.sticky.ui.gps.NeedPermissionActivity
import com.nexters.sticky.ui.main.MainActivity
import com.nexters.sticky.utils.checkMapPermission
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class MapActivity : BaseActivity<ActivityMapBinding>(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {
	override val viewModel: MapViewModel by viewModels()
	override val layoutRes = R.layout.activity_map
	override val actionBarLayoutRes = R.layout.actionbar_setaddress_layout
	override val statusBarColorRes = R.color.primary_white
	private lateinit var mMap: GoogleMap
	private var currentMarker: Marker? = null
	private val PERMISSIONS_REQUEST_CODE = 100
	var REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
	private lateinit var mFusedLocationClient: FusedLocationProviderClient
	private lateinit var locationRequest: LocationRequest

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setMapSetting()
		setOnClickListener()
		setActionBar()
	}

	override fun onMapReady(googleMap: GoogleMap) {
		mMap = googleMap
		setDefaultLocation()
		if (checkMapPermission()) {
			startLocationUpdates()
		} else {
			if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
				val intent = Intent(this@MapActivity, NeedPermissionActivity::class.java)
				startActivity(intent)
				finish()
			} else {
				checkMapPermission()
			}
		}
		mMap.uiSettings.isMyLocationButtonEnabled = true
		mMap.setOnMarkerDragListener(this)
	}

	val locationCallback: LocationCallback = object : LocationCallback() {
		override fun onLocationResult(locationResult: LocationResult) {
			super.onLocationResult(locationResult)
			val currentPosition: LatLng
			val location: Location
			val locationList = locationResult.locations
			if (locationList.size > 0) {
				location = locationList[locationList.size - 1]
				currentPosition = LatLng(location.latitude, location.longitude)
				viewModel.setAddressText("")
				viewModel.setAddressName(getCurrentAddress(currentPosition))

				//현재 위치에 마커 생성하고 이동
				setCurrentLocation(location.latitude, location.longitude)
			}
		}
	}

	private fun setMapSetting() {
		val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
		mapFragment.getMapAsync(this)
		window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
		locationRequest = LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
		val builder = LocationSettingsRequest.Builder()
		builder.addLocationRequest(locationRequest)
		mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
	}

	private fun startLocationUpdates() {
		if (!checkLocationServicesStatus()) {
			val intent = Intent(this@MapActivity, NeedPermissionActivity::class.java)
			startActivity(intent)
			finish()
		} else {
			val intent: Intent = intent
			if (intent.getBooleanExtra("findhere", false)) { // 주소 검색이 아닌 현재 위치 버튼 클릭으로 넘어온 경
				if (checkMapPermission()) {
					mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
					mMap.isMyLocationEnabled = true
				}
			} else {
				setAddressDatabinding() // 현재 위치로 주소 찾기 버튼으로 넘어 온 경우가 아닐때 - 즉, 주소 검색 후 넘어온 경우
			}

		}
	}

	override fun onStart() {
		super.onStart()
		if (intent.getBooleanExtra("findhere", false)) {
			if (checkMapPermission()) {
				mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
			}
		}
	}

	override fun onStop() {
		super.onStop()
		if (intent.getBooleanExtra("findhere", false)) {
			mFusedLocationClient.removeLocationUpdates(locationCallback)
		}
	}

	private fun setCurrentLocation(latitude: Double, longitude: Double) {
		currentMarker?.remove()
		val currentLatitueLongitude = LatLng(latitude, longitude)

		val markerOptions = MarkerOptions().apply {
			position(currentLatitueLongitude)
			draggable(true)
		}
		currentMarker = mMap.addMarker(markerOptions)
		val cameraUpdate = CameraUpdateFactory.newLatLng(currentLatitueLongitude)
		mMap.moveCamera(cameraUpdate)
	}

	override fun onMarkerDragStart(p0: Marker?) {
	}

	lateinit var LatitueLongitude: LatLng
	lateinit var markerOptions: MarkerOptions
	override fun onMarkerDrag(markerPosition: Marker) {
		LatitueLongitude = LatLng(markerPosition.position.latitude, markerPosition.position.longitude)
		val mapAddressText: String = getCurrentAddress(LatitueLongitude)
		viewModel.setAddressText("")
		viewModel.setAddressName(mapAddressText)
		markerOptions = MarkerOptions().apply {
			position(markerPosition.position)
			draggable(true)
			icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
		}
	}

	override fun onMarkerDragEnd(markerPosition: Marker?) {
		currentMarker?.remove()
		currentMarker = mMap.addMarker(markerOptions)
		val cameraUpdate = CameraUpdateFactory.newLatLngZoom(LatitueLongitude, 18f)

		mMap.moveCamera(cameraUpdate)
	}

	private fun getCurrentAddress(latlng: LatLng): String {
		val geocoder = Geocoder(this@MapActivity)

		val addresses: List<Address>? = try {
			geocoder.getFromLocation(
				latlng.latitude,
				latlng.longitude,
				1)
		} catch (ioException: IOException) {
			//네트워크 문제
			return "지오코더 서비스 사용불가"
		} catch (illegalArgumentException: IllegalArgumentException) {
			toast("잘못된 GPS 좌표")
			return "잘못된 GPS 좌표"
		}


		return if (addresses == null || addresses.isEmpty()) {
			"주소 미발견"
		} else {
			val address: Address = addresses[0]
			address.getAddressLine(0).toString()
		}

	}

	private fun checkLocationServicesStatus(): Boolean {
		val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

		return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
				|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
	}

	private fun setDefaultLocation() {
		val DEFAULT_LOCATION = LatLng(37.56, 126.97)

		currentMarker?.remove()

		val markerOptions = MarkerOptions().apply {
			position(DEFAULT_LOCATION)
			draggable(true)
			icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
		}

		currentMarker = mMap.addMarker(markerOptions)

		val cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 18f)
		mMap.moveCamera(cameraUpdate)
	}

	override fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<out String>, grandResults: IntArray) {
		super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults)
		if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.size == REQUIRED_PERMISSIONS.size) {

			// 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
			var check_result = true

			// 모든 퍼미션을 허용했는지 체크합니다.
			for (result in grandResults) {
				if (result != PackageManager.PERMISSION_GRANTED) {
					check_result = false
					break
				}
			}
			if (check_result) {
				// 퍼미션을 허용했다면 위치 업데이트를 시작합니다.
				startLocationUpdates()
			} else {
				val intent = Intent(this@MapActivity, NeedPermissionActivity::class.java)
				startActivity(intent)
				finish()
			}
		}
	}


	private fun setOnClickListener() {
		binding.setHomeBtn.setOnClickListener {
			val intent = Intent(this@MapActivity, MainActivity::class.java)
			intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
			startActivity(intent)
		}
		binding.resetAddressTxt.setOnClickListener {
			onBackPressed()
		}
	}

	private fun setActionBar() {
		actionBar.clickListener(R.id.back_main_btn) {
			onBackPressed()
		}
	}

	@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
	private fun setAddressDatabinding() { // setAddressActivity에서 넘겨준 Data 받기
		val intent: Intent = intent
		if (intent.hasExtra("address")) {
			val addressData = intent.getStringExtra("address")
			val addressNameData = intent.getStringExtra("name")
			val addresslat = intent.getDoubleExtra("latitude", 37.56)
			val addresslng = intent.getDoubleExtra("longtitude", 126.97)
			viewModel.setAddressText(addressData)
			viewModel.setAddressName(addressNameData)
			viewModel.setAddressLatitude(addresslat)
			viewModel.setAddressLongitude(addresslng)
			setCurrentLocation(addresslat, addresslng)
		} else {
			toast("전달된 이름이 없습니다")
		}
	}

}