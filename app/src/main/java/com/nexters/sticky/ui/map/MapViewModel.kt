package com.nexters.sticky.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel: ViewModel() {
	val text = MutableLiveData<String>()
//    var sampleList: LiveData<MutableList<Sample>> = MutableLiveData()

	fun setText(value: String) {
		text.value = value
	}
}