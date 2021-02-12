package com.nexters.sticky.ui.mypage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActionBar
import com.nexters.sticky.databinding.ActivityViewMoreImageContainerBinding
import com.nexters.sticky.databinding.BaseToolbarLayoutBinding

class ViewMoreDetailActivity : AppCompatActivity() {
	lateinit var baseBinding: BaseToolbarLayoutBinding
	lateinit var binding: ActivityViewMoreImageContainerBinding

	private val actionBar = BaseActionBar()

	private val titleResId = "titleResId"
	private val imageResId = "imageResId"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		baseBinding = BaseToolbarLayoutBinding.inflate(layoutInflater)
		setContentView(baseBinding.root)

		window.statusBarColor = ContextCompat.getColor(this@ViewMoreDetailActivity, R.color.white)

		setActionBar()
		setContainerView()
	}

	private fun setContainerView() {
		binding = ActivityViewMoreImageContainerBinding.inflate(layoutInflater, baseBinding.baseContainer, true)
		binding.img.setImageResource(intent.getIntExtra(imageResId, R.drawable.ic_launcher_background))
	}

	private fun setActionBar() {
		setSupportActionBar(baseBinding.toolbar)
		actionBar.apply {
			initActionBar(
				this@ViewMoreDetailActivity,
				R.layout.actionbar_view_more_layout
			)
			setLayoutBackgroundColor(R.color.white)
			setText(R.id.tv_title, getString(intent.getIntExtra(titleResId, R.string.view_more_about_sticky)))
			clickListener(R.id.btn_left) {
				finish()
			}
		}
	}
}