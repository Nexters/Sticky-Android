package com.nexters.sticky.ui.share

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityShareBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareActivity : BaseActivity<ActivityShareBinding>() {
	override val viewModel: ShareViewModel by viewModels()

	override val layoutRes = R.layout.activity_share
	override val actionBarLayoutRes = R.layout.actionbar_main_layout
	override val statusBarColorRes = R.color.purple_200

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.shareViewPager.adapter = ShareFragmentAdapter(this)

		setActionBar()
		setMediator()
	}

	private fun setActionBar() {
		actionBar.clickListener(R.id.btn_my_page) {
			toast("left button")
		}

//		actionBar.clickListener(R.id.btn_close_button) {
//			toast("right button")
//		}
//
//		actionBar.setText(R.id.tv_title, "title")
	}
	fun setMediator() {
		val tabLayoutTextArray = arrayOf("현재 기록", "누적 기록", "최근 뱃지")

		TabLayoutMediator(binding.shareTabLayout, binding.shareViewPager) { tab, position ->
			tab.text = tabLayoutTextArray[position]
		}.attach()
	}
}