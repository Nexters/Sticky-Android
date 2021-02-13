package com.nexters.sticky.ui.share

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivityShareBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

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
		val tabLayoutTextArray = arrayOf("현재 기록", "누적 기록", "최근 뱃지")

		TabLayoutMediator(binding.shareTabLayout, binding.shareViewPager) { tab, position ->
			tab.text = tabLayoutTextArray[position]
		}.attach()

	}

	private fun setOnClickListener() {
		binding.shareBtn.setOnClickListener {
			setShareImage()
			//getCaptureScreen()
		}
		binding.instagramShareBtn.setOnClickListener {
			instagramShare()
			//getCaptureScreen()
		}
	}

	private fun setShareImage() {
		//val bmp = BitmapFactory.decodeFile("${cacheDir.absolutePath}/capture.jpg")
		//val uri: Uri? = getImageUri(this, bmp)
		getCaptureScreen()
		val file = File(cacheDir, "capture.jpg")
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
//			val backgroundAssetUri: Uri =
//				Uri.parse(cacheDir.toString() + "/capture.jpg")
//			val bmp = BitmapFactory.decodeFile("$cacheDir/capture.jpg")
//			val uri: Uri? = getImageUri(this, bmp)
			getCaptureScreen()
			val file = File(cacheDir, "capture.jpg")
			val uri: Uri? = FileProvider.getUriForFile(this, packageName, file)
			val intent = Intent(android.content.Intent.ACTION_SEND)
			intent.type = "image/*"
			intent.putExtra(Intent.EXTRA_STREAM, uri)
			intent.setPackage("com.instagram.android")
			startActivity(intent)
		}
	}

	private fun getCaptureScreen() {
		val storage: String = cacheDir.toString()
		val container = binding.captureLayout
		container.buildDrawingCache()
		val captureView = container.drawingCache
		val filename = "capture.jpg"
		val tempFile = File(storage, filename)

		try {
			tempFile.createNewFile()
			val fos = FileOutputStream(tempFile)
			captureView.compress(Bitmap.CompressFormat.JPEG, 100, fos)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
	//
//	@Suppress("DEPRECATION")
//	private fun getImageUri(context: Context, inImage: Bitmap?): Uri? {
//		val bytes = ByteArrayOutputStream()
//		inImage!!.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//		val path: String = MediaStore.Images.Media.insertImage(
//			context.contentResolver,
//			inImage,
//			"Title",
//			null
//		)
//		return Uri.parse(path)
//	}
}