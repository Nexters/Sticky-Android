package com.nexters.sticky.ui.share

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShareViewModel : ViewModel() {

	val text = MutableLiveData<String>()
//    var sampleList: LiveData<MutableList<Sample>> = MutableLiveData()

	fun setText(value: String) {
		text.value = value
	}
}