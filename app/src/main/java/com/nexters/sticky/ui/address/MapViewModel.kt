package com.nexters.sticky.ui.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
	val addressText = MutableLiveData<String>()
	val latitude = MutableLiveData<Double>()
	val longtitude = MutableLiveData<Double>()

	val buildingName = MutableLiveData<String>()


	fun setAddressText(value: String) {
		addressText.value = value
	}

	fun setAddressLatitude(value: Double) {
		latitude.value = value
	}

	fun setAddressLongitude(value: Double) {
		longtitude.value = value
	}

	fun setAddressName(value: String) {
		buildingName.value = value
	}

}