package com.nexters.sticky.ui.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.animation.*
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityMainBinding
import com.nexters.sticky.ui.dialog.ExitChallengeDialog
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
	override val statusBarColorRes = R.color.stop_challenge_color

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	private val animationDuration = 1000L
	private val animationTranslationX = "translationX"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		initView()
		setOnClickListener()
		observeViewModel()
	}

	private fun initView() {
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

		binding.btnStartShareChallenge.setOnClickListener {
			when (viewModel.challengeStatus.value) {
				CHALLENGE.STOP -> viewModel.setChallengeStatus(CHALLENGE.START)
				else -> goToShareActivity()
			}
		}

		binding.btnPauseChallenge.setOnClickListener {
//			viewModel.setChallengeStatus(CHALLENGE.PAUSE)
			if (viewModel.challengeStatus.value != CHALLENGE.STOP) {
				viewModel.setChallengeStatus(CHALLENGE.STOP)
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
		stopButtonAnimation()
		setStopActionBarView()
		binding.tvCanNotStartChallenge.isVisible = viewModel.challengeStatus.value == CHALLENGE.STOP
	}

	private fun stopButtonAnimation() {
		moveHorizontally(binding.btnStartShareChallenge, 0F)
		moveHorizontally(binding.btnPauseChallenge, 0F)
//		GlobalScope.launch {
//			delay(animationDuration)
//			binding.btnStartShareChallenge.setBackgroundResource(R.drawable.main_button_background_disable)
//		}
	}

	private fun setStopActionBarView() {
		actionBar.setIsVisible(R.id.btn_right)
		actionBar.setText(R.id.tv_title, "외출중입니다")

		actionBar.setLayoutBackgroundColor(R.color.stop_challenge_color)
		window.statusBarColor = ContextCompat.getColor(this, R.color.stop_challenge_color)
		binding.root.setBackgroundColor(resources.getColor(R.color.stop_challenge_color, null))
	}

	private fun setStartView() {
		startButtonAnimation()
		startActionBarView()
		binding.tvCanNotStartChallenge.isVisible = viewModel.challengeStatus.value == CHALLENGE.STOP
	}

	private fun startButtonAnimation() {
		binding.btnStartShareChallenge.setBackgroundResource(R.drawable.main_button_background_black)
		moveHorizontally(binding.btnStartShareChallenge, 80F)
		moveHorizontally(binding.btnPauseChallenge, -80F)
	}

	private fun startActionBarView() {
		actionBar.clickListener(R.id.btn_right) {
			ExitChallengeDialog().show(supportFragmentManager, "")
		}

		actionBar.setLayoutBackgroundColor(R.color.brand_color)
		window.statusBarColor = ContextCompat.getColor(this, R.color.brand_color)
		binding.root.setBackgroundColor(resources.getColor(R.color.brand_color, null))
	}

	private fun moveHorizontally(view: View, distance: Float) {
		val moveDistance = TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP,
			distance,
			binding.root.resources.displayMetrics
		)

		val animation = ObjectAnimator.ofFloat(view, animationTranslationX, moveDistance)
		animation.duration = animationDuration
		animation.start()
	}
}