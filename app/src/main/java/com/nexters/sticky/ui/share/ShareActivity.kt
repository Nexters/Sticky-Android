package com.nexters.sticky.ui.share

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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

		val tabLayoutTextArray = arrayOf("현재 기록","누적 기록", "최근 뱃지")

		TabLayoutMediator(binding.shareTabLayout, binding.shareViewPager){tab,position ->
			tab.text = tabLayoutTextArray[position]
		}.attach()
	}
}