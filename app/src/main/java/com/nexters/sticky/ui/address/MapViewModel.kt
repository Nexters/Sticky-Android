package com.nexters.sticky.ui.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
	val addressText = MutableLiveData<String>()
	val mapAddressText = MutableLiveData<String>()
	val guideText = MutableLiveData<String>()

	fun setAddressText(value: String) {
		addressText.value = value
	}

	fun setMapAddressText(value: String) {
		mapAddressText.value = value
	}

	fun removeGuideText() {
		guideText.value = ""
	}

	fun setGuideText(value: String) {
		guideText.value = value
	}

}