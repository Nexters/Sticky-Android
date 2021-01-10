package com.nexters.sticky.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.data.entity.Sample
import com.nexters.sticky.databinding.ActivityMainBinding
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

	override val viewModel: MainViewModel by viewModels()
	override val getLayoutRes = R.layout.activity_main

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		viewModel.setText("어딜나가조")
	}
}