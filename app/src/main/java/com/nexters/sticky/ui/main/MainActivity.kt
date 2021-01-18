package com.nexters.sticky.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityMainBinding
import com.nexters.sticky.ui.share.ShareActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
	override val viewModel: MainViewModel by viewModels()

	override val layoutRes = R.layout.activity_main
	override val actionBarLayoutRes = R.layout.actionbar_main_layout
	override val statusBarColorRes = R.color.black

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setActionBar()
		setOnClickListener()

		setTimeText()
	}

	private fun setActionBar() {
		actionBar.clickListener(R.id.btn_my_page) {
			toast("go to my page")
		}

		actionBar.clickListener(R.id.btn_close_button) {
			toast("stop challenge")
		}

		actionBar.setVisibility(R.id.img_heart)
		actionBar.setText(R.id.tv_title, "3")

//		actionBar.setText(R.id.tv_title, "외출중입니다")
	}

	private fun setOnClickListener() {
		binding.btnShare.setOnClickListener {
			val intent = Intent(this@MainActivity, ShareActivity::class.java)
			startActivity(intent)
		}
	}

	private fun setTimeText() {
		with(viewModel) {
			setTextDay("11")
			setTextHour("12")
			setTextMinute("50")
			setTextSecond("44")
		}
	}
}