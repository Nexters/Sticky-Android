package com.nexters.sticky.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

const val LOCATION_PERMISSION_REQUEST_CODE = 9001

fun Context.checkLocationPermission(): Boolean {
	val accessWifiStatePermission = checkPermission(android.Manifest.permission.ACCESS_WIFI_STATE)
	val changeWifiStatePermission = checkPermission(android.Manifest.permission.CHANGE_WIFI_STATE)
	val accessFineLocationPermission = checkPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
	val accessBackgroundLocationPermission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
		checkPermission(android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
	} else true

	if (accessWifiStatePermission && changeWifiStatePermission && accessFineLocationPermission && accessBackgroundLocationPermission) {
		return true
	}

	return false
}

fun Context.checkPermission(permission: String): Boolean {
	val isPermissionGranted = ActivityCompat.checkSelfPermission(this, permission)
	return isPermissionGranted == PackageManager.PERMISSION_GRANTED
}

fun Context.checkMapPermission(): Boolean {
	val hasFineLocationPermission = ContextCompat.checkSelfPermission(this,
		Manifest.permission.ACCESS_FINE_LOCATION)
	val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(this,
		Manifest.permission.ACCESS_COARSE_LOCATION)
	return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
			hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
}

fun Activity.requestLocationPermission() {
	val permissionArray =
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
			arrayOf(
				android.Manifest.permission.ACCESS_WIFI_STATE,
				android.Manifest.permission.CHANGE_WIFI_STATE,
				android.Manifest.permission.ACCESS_FINE_LOCATION,
				android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
		} else {
			arrayOf(android.Manifest.permission.ACCESS_WIFI_STATE, android.Manifest.permission.CHANGE_WIFI_STATE, android.Manifest.permission.ACCESS_FINE_LOCATION)
		}

	ActivityCompat.requestPermissions(this, permissionArray, LOCATION_PERMISSION_REQUEST_CODE)
}
