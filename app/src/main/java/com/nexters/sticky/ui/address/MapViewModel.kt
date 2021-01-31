package com.nexters.sticky.ui.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {
	val addressText = MutableLiveData<String>()
	val mapAddressText = MutableLiveData<String>()
	val layoutaddress = MutableLiveData<String>()

	fun setAddressText(value: String) {
		addressText.value = value
	}
	fun setMapAddressText(value: String) {
//		if(addressText == null){
//			layoutaddress.value = value
//		}
//		else{
//			layoutaddress.value = addressText.value
//		}
		mapAddressText.value = value
	}



}