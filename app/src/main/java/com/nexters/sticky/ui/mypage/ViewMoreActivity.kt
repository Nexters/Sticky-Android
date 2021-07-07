package com.nexters.sticky.ui.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityViewMoreBinding
import com.nexters.sticky.ui.dialog.ChangeAddressDialog
import com.nexters.sticky.ui.mypage.ViewMore.*


class ViewMoreActivity : BaseActivity<ActivityViewMoreBinding>() {
	override val viewModel: MyPageViewModel by viewModels()

	override val layoutRes = R.layout.activity_view_more
	override val actionBarLayoutRes = R.layout.actionbar_main_layout
	override val statusBarColorRes = R.color.primary_white

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	private val googlePlayStoreUrl = "https://play.google.com/store"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setActionBar()
		setOnClickListener()
	}

	private fun setActionBar() {
		actionBar.setText(R.id.tv_title, getString(R.string.view_more_title))

		actionBar.setImage(R.id.btn_left, R.drawable.ic_icon_back)
		actionBar.clickListener(R.id.btn_left) {
			finish()
		}
	}

	private fun setOnClickListener() {
		binding.apply {
			changeHomeAddressLayout.setOnClickListener {
				ChangeAddressDialog().show(supportFragmentManager, "")
			}

			aboutStickyLayout.setOnClickListener {
				ABOUT.goToDetailActivity(this@ViewMoreActivity)
			}

			versionInfoLayout.setOnClickListener {
				val intent = Intent(Intent.ACTION_VIEW, Uri.parse(googlePlayStoreUrl))
				startActivity(intent)
			}

			openSourceLicenseLayout.setOnClickListener {
				LICENSE.goToDetailActivity(this@ViewMoreActivity)
			}
		}
	}
}