package com.nexters.sticky.ui.share

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityShareBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.io.File


@AndroidEntryPoint
class ShareActivity : BaseActivity<ActivityShareBinding>() {
	override val viewModel: ShareViewModel by viewModels()

	override val layoutRes = R.layout.activity_share
	override val actionBarLayoutRes = R.layout.actionbar_share_layout
	override val statusBarColorRes = R.color.level1_back

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.shareViewPager.adapter = ShareFragmentAdapter(this)
		binding.shareViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
		binding.shareViewPager.offscreenPageLimit = 2

		val pageMargin = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
		val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

		binding.shareViewPager.setPageTransformer { page, position ->
			val myOffset = position * -(2 * pageOffset + pageMargin)
			if (binding.shareViewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
				if (ViewCompat.getLayoutDirection(binding.shareViewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
					page.translationX = -myOffset
				} else {
					page.translationX = myOffset
				}
			} else {
				page.translationY = myOffset
			}
		}

		viewModel.setText("현재 기록")
		setActionBar()
		setMediator()
		setOnClickListener()
	}


	private fun setActionBar() {
		actionBar.clickListener(R.id.back_main_btn) {
			onBackPressed()
		}

		actionBar.clickListener(R.id.image_save_btn) {
			toast("right button")
		}
	}

	fun setMediator() {
		val tabLayoutTextArray = arrayOf("현재 기록", "최근 뱃지")

		TabLayoutMediator(binding.shareTabLayout, binding.shareViewPager) { tab, position ->
			tab.text = tabLayoutTextArray[position]
		}.attach()

	}

	private fun setOnClickListener() {
		binding.shareBtn.setOnClickListener {
			setShareImage()
		}
		binding.instagramShareBtn.setOnClickListener {
			instagramShare()
		}
	}

	private fun setShareImage() {
		val file = File(cacheDir, "capturebadge.jpg")
		val uri: Uri? = FileProvider.getUriForFile(this, packageName, file)
		val intent = Intent(android.content.Intent.ACTION_SEND)
		intent.type = "image/*"
		intent.putExtra(Intent.EXTRA_STREAM, uri)

		val shareIntent = Intent.createChooser(intent, null)
		startActivity(shareIntent)
	}

	private fun instagramShare() {

		val launchIntent = packageManager.getLaunchIntentForPackage("com.instagram.android")

		if (launchIntent == null) { // 단말기 내에 어플리케이션(앱)이 설치되어 있지 않음.
			val intent = Intent(
				Intent.ACTION_VIEW,
				Uri.parse("market://details?id=" + "com.instagram.android")
			)
			startActivity(intent)          // 앱이 설치되어 있지 않은 경우 Play스토어로 이동
		} else {
			val file = File(cacheDir, "capturerecord.jpg")
			val uri: Uri? = FileProvider.getUriForFile(this, packageName, file)
			Timber.d(cacheDir.toString())
			val intent = Intent(android.content.Intent.ACTION_SEND)
			intent.type = "image/*"
			intent.putExtra(Intent.EXTRA_STREAM, uri)
			intent.setPackage("com.instagram.android")
			startActivity(intent)
		}
	}
}