package com.nexters.sticky.ui.mypage

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityMyPageBinding
import com.nexters.sticky.ui.mypage.ViewMore.LEVEL
import com.nexters.sticky.ui.mypage.adapter.MyPageAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPageActivity : BaseActivity<ActivityMyPageBinding>() {

	override val viewModel: MyPageViewModel by viewModels()

	override val layoutRes = R.layout.activity_my_page
	override val actionBarLayoutRes = R.layout.actionbar_main_layout
	override val statusBarColorRes = R.color.primary_white

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setActionBar()
		setRecyclerView()
		setOnClickListener()

//		viewModel.getLevelInfo()
	}

	private fun setActionBar() {
		actionBar.setText(R.id.tv_title, getString(R.string.my_page_title))

		actionBar.setImage(R.id.btn_left, R.drawable.ic_icon_close)
		actionBar.clickListener(R.id.btn_left) {
			finish()
		}

		actionBar.setImage(R.id.btn_right, R.drawable.ic_icon_more)
		actionBar.clickListener(R.id.btn_right) {
			val intent = Intent(this@MyPageActivity, ViewMoreActivity::class.java)
			startActivity(intent)
		}
	}

	private fun setRecyclerView() {
		binding.recyclerView.apply {
			adapter = MyPageAdapter()
			layoutManager = GridLayoutManager(this@MyPageActivity, 3).apply {
				spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
					override fun getSpanSize(position: Int): Int {
						return when ((adapter as MyPageAdapter).getItemViewType(position)) {
							MyPageAdapter.BADGE.TITLE.ordinal -> spanCount
							MyPageAdapter.BADGE.ITEM.ordinal -> 1
							else -> -1
						}
					}
				}
			}
		}
	}

	private fun setOnClickListener() {
		binding.tvLevelInfo.setOnClickListener {
			LEVEL.goToDetailActivity(this@MyPageActivity)
		}
	}
}