package com.nexters.sticky.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nexters.sticky.data.repository.SampleRepository

class MainViewModel @ViewModelInject constructor(private val sampleRepository: SampleRepository) : ViewModel() {
	enum class CHALLENGE {
		START, PAUSE, STOP
	}

	val challengeStatus = MutableLiveData(CHALLENGE.STOP)

	val day = MutableLiveData("0일")
	val hour = MutableLiveData("00")
	val minute = MutableLiveData("00")
	val second = MutableLiveData("00")

	fun setChallengeStatus(status: CHALLENGE) {
		challengeStatus.value = status
	}

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