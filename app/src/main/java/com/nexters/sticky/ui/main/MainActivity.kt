package com.nexters.sticky.ui.main

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.animation.*
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityMainBinding
import com.nexters.sticky.ui.main.adapter.MainAdapter
import com.nexters.sticky.ui.main.adapter.MainItemDecorator
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

		setRecyclerView()
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
//		binding.btnShare.setOnClickListener {
//			val intent = Intent(this@MainActivity, ShareActivity::class.java)
//			startActivity(intent)
//		}

		binding.btnStartChallenge.setOnClickListener {
			startAnimation()
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

	private fun setRecyclerView() {
		binding.recyclerView.apply {
			adapter = MainAdapter()
			layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
			addItemDecoration(MainItemDecorator())
		}
	}

	private fun startAnimation() {
		binding.btnStartChallenge.setBackgroundResource(R.drawable.main_button_background_black)
		moveHorizontally(binding.btnStartChallenge, 80F)
		moveHorizontally(binding.btnPauseChallenge, -80F)
	}

	private fun moveHorizontally(view: View, distance: Float) {
		val moveDistance = TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP,
			distance,
			binding.root.resources.displayMetrics
		)

		val animation = ObjectAnimator.ofFloat(view, "translationX", moveDistance)
		animation.duration = 1000
		animation.start()
	}
}