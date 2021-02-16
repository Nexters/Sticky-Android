package com.nexters.sticky.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.sticky.R
import com.nexters.sticky.data.repository.SampleRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainViewModel @ViewModelInject constructor(private val sampleRepository: SampleRepository) : ViewModel() {
	enum class CHALLENGE {
		START, PAUSE, STOP
	}

	enum class BACKGROUND(val colorResId: Int) {
		STOP(R.color.main_disable_background),
		ONE(R.color.main_level_one_background),
		TWO(R.color.main_level_two_background),
		THREE(R.color.main_level_three_background),
		FOUR(R.color.main_level_four_background)
	}

	val challengeStatus = MutableLiveData(CHALLENGE.STOP)
	val backgroundColor = MutableLiveData(BACKGROUND.THREE.colorResId)
	val userLevel = BACKGROUND.ONE // TODO : SharedPreference 에서 정보 가져오기

	fun isUserHome() = true
	fun isChallengeStarted() = challengeStatus.value == CHALLENGE.START

	val day = MutableLiveData("0 day")
	val hour = MutableLiveData("00")
	val minute = MutableLiveData("00")
	val second = MutableLiveData("00")

	private val format = "%02d"
	private val formatDay = "%d day"
	private val base60 = 60
	private val base24 = 24
	private val durationTimeMillis = 1000L

	fun setChallengeStatus(status: CHALLENGE) {
		challengeStatus.value = status
	}

	fun setBackgroundColor(colorResId: Int) {
		backgroundColor.value = colorResId
	}

	private var job = Job()
		get() {
			if (field.isCancelled) field = Job()
			return field
		}

	fun stopTimer() {
		job.cancel()
		resetTimerText()
	}

	fun startTimer() {
		resetTimerText()
		viewModelScope.launch(job) {
			val totalSeconds = TimeUnit.DAYS.toSeconds(2)
			val tickSeconds = 1

			for (seconds in tickSeconds..totalSeconds) {
				delay(durationTimeMillis)

				second.value = String.format(format, TimeUnit.SECONDS.toSeconds(seconds) % base60)
				minute.value = String.format(format, TimeUnit.SECONDS.toMinutes(seconds) % base60)
				hour.value = String.format(format, TimeUnit.SECONDS.toHours(seconds) % base24)
				day.value = String.format(formatDay, TimeUnit.SECONDS.toDays(seconds))
			}
		}
	}

	private fun resetTimerText() {
		day.value = "0 day"
		hour.value = "00"
		minute.value = "00"
		second.value = "00"
	}
}