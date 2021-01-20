package com.nexters.sticky.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nexters.sticky.data.repository.SampleRepository

class MainViewModel @ViewModelInject constructor(private val sampleRepository: SampleRepository) :
	ViewModel() {

	val day = MutableLiveData("0일")
	val hour = MutableLiveData("00")
	val minute = MutableLiveData("00")
	val second = MutableLiveData("00")

	fun setTextDay(value: String) {
		day.value = value + "일"
	}

	fun setTextHour(value: String) {
		hour.value = value
	}

	fun setTextMinute(value: String) {
		hour.value = value
	}

	fun setTextSecond(value: String) {
		second.value = value
	}
}