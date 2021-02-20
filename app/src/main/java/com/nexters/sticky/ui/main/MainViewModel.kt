package com.nexters.sticky.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.sticky.data.entity.Reward
import com.nexters.sticky.data.repository.RewardRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainViewModel @ViewModelInject constructor(private val rewardRepository: RewardRepository) : ViewModel() {
	enum class CHALLENGE {
		START, PAUSE, STOP
	}

	val challengeStatus = MutableLiveData(CHALLENGE.STOP)

	val day = MutableLiveData("0일")
	val hour = MutableLiveData("00")
	val minute = MutableLiveData("00")
	val second = MutableLiveData("00")

	private val format = "%02d"
	private val formatDay = "%d일"
	private val base60 = 60
	private val base24 = 24
	private val durationTimeMillis = 1000L

//	val result: LiveData<List<Reward>> = rewardRepository.fetchUserLevelInfo(2)

	fun setChallengeStatus(status: CHALLENGE) {
		challengeStatus.value = status
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
		day.value = "0일"
		hour.value = "00"
		minute.value = "00"
		second.value = "00"
	}
}