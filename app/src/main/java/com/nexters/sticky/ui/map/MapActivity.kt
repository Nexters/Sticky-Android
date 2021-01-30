package com.nexters.sticky.ui.map

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityMapBinding
import com.nexters.sticky.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException


@AndroidEntryPoint
class MapActivity : BaseActivity<ActivityMapBinding>(), OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
	override val viewModel: MapViewModel by viewModels()

	override val layoutRes = R.layout.activity_map
	override val actionBarLayoutRes = R.layout.actionbar_setaddress_layout
	override val statusBarColorRes = R.color.purple_200
	private lateinit var mMap: GoogleMap
	private var currentMarker: Marker? = null
	private var GPS_ENABLE_REQUEST_CODE = 2001
	private var UPDATE_INTERVAL_MS = 1000
	private var FASTEST_UPDATE_INTERVAL_MS = 500
	private var PERMISSIONS_REQUEST_CODE = 100
	var needRequest = false
	var REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
	lateinit var mCurrentLocation: Location
	lateinit var currentPosition: LatLng
	private lateinit var mFusedLocationClient: FusedLocationProviderClient
	private lateinit var locationRequest: LocationRequest
	private lateinit var location: Location
	private lateinit var mLayout: View

	override fun setUpBinding() {
		binding.vm = viewModel
		mLayout = binding.mapLayout
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.setText("주소주소주소주소주소주소주소주소")

		window.setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

		locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
			.setInterval(UPDATE_INTERVAL_MS.toLong())
			.setFastestInterval(FASTEST_UPDATE_INTERVAL_MS.toLong())

		val builder = LocationSettingsRequest.Builder()
		builder.addLocationRequest(locationRequest)
		mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

		val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
		mapFragment.getMapAsync(this)

		setOnClickListener()
		setActionBar()
	}

	override fun onMapReady(googleMap: GoogleMap) {
		mMap = googleMap
		setDefaultLocation()

		val hasFineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
		val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

		if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
			startLocationUpdates()
		} else {
			if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
				Snackbar.make(mLayout, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Snackbar.LENGTH_INDEFINITE).setAction("확인") {
					ActivityCompat.requestPermissions(this@MapActivity, REQUIRED_PERMISSIONS,
						PERMISSIONS_REQUEST_CODE)
				}.show()
			} else {
				ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
			}
		}

		mMap.uiSettings.isMyLocationButtonEnabled = true
		mMap.setOnMapClickListener { }

		val marker = LatLng(37.555449721383795, 126.97578942646165)
		mMap.addMarker(MarkerOptions().position(marker).title("Marker LAB"))
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 18F))
	}

	val locationCallback: LocationCallback = object : LocationCallback() {
		override fun onLocationResult(locationResult: LocationResult) {
			super.onLocationResult(locationResult)
			val locationList = locationResult.locations
			if (locationList.size > 0) {
				location = locationList[locationList.size - 1]
				//location = locationList.get(0);
				currentPosition = LatLng(location.latitude, location.longitude)
				val markerTitle: String = getCurrentAddress(currentPosition)
				val markerSnippet = "위도:" + location.latitude.toString() + " 경도:" + location.longitude.toString()

				//현재 위치에 마커 생성하고 이동
				setCurrentLocation(location, markerTitle, markerSnippet)
				mCurrentLocation = location
			}
		}
	}

	private fun startLocationUpdates() {
		if (!checkLocationServicesStatus()) {
			showDialogForLocationServiceSetting()
		} else {
			val hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_FINE_LOCATION)
			val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
				Manifest.permission.ACCESS_COARSE_LOCATION)
			if (hasFineLocationPermission != PackageManager.PERMISSION_GRANTED ||
				hasCoarseLocationPermission != PackageManager.PERMISSION_GRANTED
			) {
				//Log.d(TAG, "startLocationUpdates : 퍼미션 안가지고 있음")
				return
			}
			//Log.d(TAG, "startLocationUpdates : call mFusedLocationClient.requestLocationUpdates")
			mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
			if (checkPermission()) mMap.isMyLocationEnabled = true
		}
	}

	override fun onStart() {
		super.onStart()
		if (checkPermission()) {

			//Log.d(TAG, "onStart : call mFusedLocationClient.requestLocationUpdates");
			mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)

			if (mMap != null)
				mMap.isMyLocationEnabled = true

		}

	}

	override fun onStop() {
		super.onStop()
		if (mFusedLocationClient != null) {

			//Log.d(TAG, "onStop : call stopLocationUpdates");
			mFusedLocationClient.removeLocationUpdates(locationCallback)
		}
	}

	private fun setCurrentLocation(location: Location?, markerTitle: String, markerSnippet: String) {
		if (currentMarker != null) currentMarker!!.remove()


		val currentLatLng = LatLng(location!!.latitude, location.longitude)

		val markerOptions = MarkerOptions()
		markerOptions.position(currentLatLng)
		markerOptions.title(markerTitle)
		markerOptions.snippet(markerSnippet)
		markerOptions.draggable(true)


		currentMarker = mMap.addMarker(markerOptions)

		val cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng)
		mMap.moveCamera(cameraUpdate)
	}

	private fun getCurrentAddress(latlng: LatLng): String {
		val geocoder = Geocoder(this@MapActivity)

		val addresses: List<Address>?

		addresses = try {
			geocoder.getFromLocation(
				latlng.latitude,
				latlng.longitude,
				1)
		} catch (ioException: IOException) {
			//네트워크 문제
			Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show()
			return "지오코더 서비스 사용불가"
		} catch (illegalArgumentException: IllegalArgumentException) {
			Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
			return "잘못된 GPS 좌표"
		}


		return if (addresses == null || addresses.isEmpty()) {
			Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show()
			"주소 미발견"
		} else {
			val address: Address = addresses[0]
			address.getAddressLine(0).toString()
		}

	}

	private fun showDialogForLocationServiceSetting() {
		val builder = AlertDialog.Builder(this)
		builder.setTitle("위치 서비스 비활성화")
		builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하실래요?")
		builder.setCancelable(true)
		builder.setPositiveButton("설정", DialogInterface.OnClickListener { dialog, id ->
			val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
			startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
		})
		builder.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
		builder.create().show()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		when (requestCode) {
			GPS_ENABLE_REQUEST_CODE ->
				//사용자가 GPS 활성 시켰는지 검사
				if (checkLocationServicesStatus()) {
					if (checkLocationServicesStatus()) {
						//Log.d(TAG, "onActivityResult : GPS 활성화 되있음")
						needRequest = true
						return
					}
				}
		}
	}

	private fun checkLocationServicesStatus(): Boolean {
		val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

		return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
				|| locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
	}

	private fun setDefaultLocation() {
		val DEFAULT_LOCATION = LatLng(37.56, 126.97)
		val markerTitle = "위치정보 가져올 수 없음"
		val markerSnippet = "위치 퍼미션과 GPS 활성 요부 확인하세요"


		if (currentMarker != null) currentMarker!!.remove()

		val markerOptions = MarkerOptions()
		markerOptions.position(DEFAULT_LOCATION)
		markerOptions.title(markerTitle)
		markerOptions.snippet(markerSnippet)
		markerOptions.draggable(true)
		markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
		currentMarker = mMap.addMarker(markerOptions)

		val cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f)
		mMap.moveCamera(cameraUpdate)
	}

	private fun checkPermission(): Boolean {
		val hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
			Manifest.permission.ACCESS_FINE_LOCATION)
		val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
			Manifest.permission.ACCESS_COARSE_LOCATION)
		return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
				hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
	}

	override fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<out String>, grandResults: IntArray) {
		if (permsRequestCode === PERMISSIONS_REQUEST_CODE && grandResults.size === REQUIRED_PERMISSIONS.size) {

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
				// 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
				if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
					|| ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])
				) {


					// 사용자가 거부만 선택한 경우에는 앱을 다시 실행하여 허용을 선택하면 앱을 사용할 수 있습니다.
					Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요. ",
						Snackbar.LENGTH_INDEFINITE).setAction("확인") { finish() }.show()
				} else {


					// "다시 묻지 않음"을 사용자가 체크하고 거부를 선택한 경우에는 설정(앱 정보)에서 퍼미션을 허용해야 앱을 사용할 수 있습니다.
					Snackbar.make(mLayout, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ",
						Snackbar.LENGTH_INDEFINITE).setAction("확인") { finish() }.show()
				}
			}
		}
	}

	private fun setOnClickListener() {
		binding.setHomeBtn.setOnClickListener {
			val intent = Intent(this@MapActivity, MainActivity::class.java)
			startActivity(intent)
		}
	}

	private fun setActionBar() {
		actionBar.clickListener(R.id.back_main_btn) {
			onBackPressed()
		}
	}
}