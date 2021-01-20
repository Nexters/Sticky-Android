package com.nexters.sticky.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityMainBinding
import com.nexters.sticky.ui.dialog.ExitChallengeDialog
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
		viewModel.setText("activity_main.xml")

		setOnClickListener()
	}

	private fun setActionBar() {
		actionBar.clickListener(R.id.btn_my_page) {
			toast("left button")
		}

		actionBar.clickListener(R.id.btn_close_button) {
			ExitChallengeDialog().show(supportFragmentManager, "")
		}

		actionBar.setText(R.id.tv_title, "title")
	}

	private fun setOnClickListener() {
		binding.btnShare.setOnClickListener {
			val intent = Intent(this@MainActivity, ShareActivity::class.java)
			startActivity(intent)
		}
	}
}