package com.nexters.sticky.ui.share

import android.os.Bundle
import androidx.activity.viewModels
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityShareBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareActivity : BaseActivity<ActivityShareBinding>() {

	override val viewModel: ShareViewModel by viewModels()
	override val getLayoutRes = R.layout.activity_share

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.shareViewPager.adapter =  ShareFragmentAdapter(this)

	}
}