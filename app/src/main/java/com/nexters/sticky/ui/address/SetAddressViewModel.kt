package com.nexters.sticky.ui.address

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetAddressViewModel : ViewModel() {
	val text = MutableLiveData<String>()
//    var sampleList: LiveData<MutableList<Sample>> = MutableLiveData()

	fun setText(value: String) {
		text.value = value
	}
}