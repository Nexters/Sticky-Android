package com.nexters.sticky.ui.share

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nexters.sticky.data.repository.SampleRepository

class ShareViewModel: ViewModel() {

	val text = MutableLiveData<String>()
//    var sampleList: LiveData<MutableList<Sample>> = MutableLiveData()

	fun setText(value: String) {
		text.value = value
	}
}