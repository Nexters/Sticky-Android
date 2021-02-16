package com.nexters.sticky.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.*
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityMainBinding
import com.nexters.sticky.ui.dialog.ExitChallengeDialog
import com.nexters.sticky.ui.main.MainViewModel.BACKGROUND
import com.nexters.sticky.ui.main.MainViewModel.CHALLENGE
import com.nexters.sticky.ui.main.adapter.MainAdapter
import com.nexters.sticky.ui.main.adapter.MainItemDecorator
import com.nexters.sticky.ui.mypage.MyPageActivity
import com.nexters.sticky.ui.share.ShareActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
	override val viewModel: MainViewModel by viewModels()

	override val layoutRes = R.layout.activity_main
	override val actionBarLayoutRes = R.layout.actionbar_main_layout
	override val statusBarColorRes = R.color.grayscale_gray_500

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		initView()
		setOnClickListener()
		observeViewModel()
	}

	private fun initView() {
		actionBar.setImage(R.id.btn_left, R.drawable.ic_icon_menu)
		setRecyclerView()
	}

	private fun setRecyclerView() {
		binding.recyclerView.apply {
			adapter = MainAdapter()
			layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
			addItemDecoration(MainItemDecorator())
		}
	}

	private fun setOnClickListener() {
		actionBar.clickListener(R.id.btn_left) {
			val intent = Intent(this@MainActivity, MyPageActivity::class.java)
			startActivity(intent)
		}

		binding.apply {
			tvDay.setOnClickListener { // START / STOP 전환 잘 되는지 테스트 용
				if (viewModel.isChallengeStarted()) viewModel.setChallengeStatus(CHALLENGE.STOP)
				else viewModel.setChallengeStatus(CHALLENGE.START)
			}

			layoutStartShareChallenge.setOnClickListener {
				when (viewModel.challengeStatus.value) {
					CHALLENGE.START -> goToShareActivity()
					CHALLENGE.STOP -> viewModel.setChallengeStatus(CHALLENGE.START)
					else -> {
					}
				}
			}

			layoutGoOut.setOnClickListener {
				toast("외출권")
			}
		}
	}

	private fun goToShareActivity() {
		val intent = Intent(this@MainActivity, ShareActivity::class.java)
		startActivity(intent)
	}

	private fun observeViewModel() {
		viewModel.challengeStatus.observe(this@MainActivity, Observer { status ->
			when (status) {
				CHALLENGE.START -> {
					setStartView()
					viewModel.startTimer()
				}
				CHALLENGE.STOP -> {
					setStopView()
					viewModel.stopTimer()
				}
				else -> toast("pause")
			}
		})
	}

	private fun setStopView() {
		setBackgroundColor(BACKGROUND.STOP.colorResId)

		actionBar.setIsVisible(R.id.btn_right)

		binding.btnCanNotStartChallenge.isVisible = !viewModel.isUserHome()
		binding.layoutStartShareChallenge.isVisible = viewModel.isUserHome()
		binding.layoutGoOut.visibility = View.GONE

		if (viewModel.isUserHome()) {
			binding.tvStartShare.text = this@MainActivity.resources.getString(R.string.main_start)
			binding.imgStartShare.setImageResource(R.drawable.ic_icon_start_white)
		}
	}

	private fun setStartView() {
		setBackgroundColor(viewModel.userLevel.colorResId)

		actionBar.setImage(R.id.btn_right, R.drawable.ic_icon_exit)
		actionBar.clickListener(R.id.btn_right) {
			ExitChallengeDialog().show(supportFragmentManager, "")
		}

		with(binding) {
			btnCanNotStartChallenge.visibility = View.INVISIBLE
			layoutStartShareChallenge.visibility = View.VISIBLE
			layoutGoOut.visibility = View.VISIBLE

			tvStartShare.text = this@MainActivity.resources.getString(R.string.main_share)
			imgStartShare.setImageResource(R.drawable.ic_icon_share_white)
		}
	}

	private fun setBackgroundColor(colorResId: Int) {
		actionBar.setLayoutBackgroundColor(colorResId)
		window.statusBarColor = this.resources.getColor(colorResId, null)
		viewModel.setBackgroundColor(colorResId)
	}
}