package com.nexters.sticky.ui.share

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityShareBinding
import com.nexters.sticky.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class ShareActivity : BaseActivity<ActivityShareBinding>() {
	override val viewModel: ShareViewModel by viewModels()

	override val layoutRes = R.layout.activity_share
	override val actionBarLayoutRes = R.layout.actionbar_share_layout
	override val statusBarColorRes = R.color.purple_200

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding.shareViewPager.adapter = ShareFragmentAdapter(this)
		viewModel.setText("현재 기록")
		setActionBar()
		setMediator()
		setOnClickListener()
	}

	private fun setActionBar() {
		actionBar.clickListener(R.id.back_main_btn) {
			val intent = Intent(this, MainActivity::class.java)
			startActivity(intent)
		}

		actionBar.clickListener(R.id.image_save_btn) {
			toast("right button")
		}
	}
	fun setMediator() {
		val tabLayoutTextArray = arrayOf("현재 기록", "누적 기록", "최근 뱃지")

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

	private fun getImageUri(context: Context, inImage: Bitmap?): Uri? {
		val bytes = ByteArrayOutputStream()
		inImage!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
		val path: String = MediaStore.Images.Media.insertImage(
			context.contentResolver,
			inImage,
			"Title",
			null
		)
		return Uri.parse(path)
	}

	private fun setShareImage(){
		val bmp = BitmapFactory.decodeResource(resources, R.drawable.abc)
		val uri: Uri? = getImageUri(this, bmp)
		val intent = Intent(android.content.Intent.ACTION_SEND)
		intent.type = "image/*"
		intent.putExtra(Intent.EXTRA_STREAM, uri)

		val shareIntent = Intent.createChooser(intent, null)
		startActivity(shareIntent)
	}

	private fun instagramShare(){
		val launchIntent = packageManager.getLaunchIntentForPackage("com.instagram.android")

		if (launchIntent == null) { // 단말기 내에 어플리케이션(앱)이 설치되어 있지 않음.
			val intent = Intent(
				Intent.ACTION_VIEW,
				Uri.parse("market://details?id=" + "com.instagram.android")
			)
			startActivity(intent)          // 앱이 설치되어 있지 않은 경우 Play스토어로 이동
		} else {
			val backgroundAssetUri: Uri =
				Uri.parse("android.resource://$packageName/drawable/abc")
			val intent = Intent(android.content.Intent.ACTION_SEND)
			intent.type = "image/*"
			intent.putExtra(Intent.EXTRA_STREAM, backgroundAssetUri)
			intent.setPackage("com.instagram.android")

			startActivity(intent)
		}
	}

}