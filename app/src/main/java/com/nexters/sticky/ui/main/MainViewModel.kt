package com.nexters.sticky.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.nexters.sticky.data.entity.Sample
import com.nexters.sticky.data.repository.SampleRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel @ViewModelInject constructor(private val sampleRepository: SampleRepository) :
	ViewModel() {

	val text = MutableLiveData<String>()
//    var sampleList: LiveData<MutableList<Sample>> = MutableLiveData()

	fun setText(value: String) {
		text.value = value
	}

//    fun insertInfo(sample: Sample) {
//        viewModelScope.launch {
//            sampleRepository.insertData(sample)
//        }
//    }
}