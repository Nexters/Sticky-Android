package com.nexters.sticky.ui.share

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseFragment
import com.nexters.sticky.databinding.FragmentShareRecordBinding
import com.nexters.sticky.utils.convertFloatToDp
import java.io.File
import java.io.FileOutputStream


class ShareRecordFragment : BaseFragment<FragmentShareRecordBinding>() {
	override val viewModel: ShareViewModel by viewModels()
	override val layoutResId = R.layout.fragment_share_record

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setBackground()
		getCaptureScreen()
	}

	private fun getCaptureScreen() {
		val storage: String = context?.cacheDir.toString()
		val container = binding.captureLayoutRecord

		container.viewTreeObserver.addOnGlobalLayoutListener {
			container.buildDrawingCache()
			val captureView = container.drawingCache
			val filename = "capturerecord.jpg"
			val tempFile = File(storage, filename)
			try {
				tempFile.createNewFile()
				val fos = FileOutputStream(tempFile)
				captureView?.compress(Bitmap.CompressFormat.JPEG, 80, fos)
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
	}

	private fun setBackground() {
		val roundedCorners = binding.root.convertFloatToDp(16F)

		Glide.with(this@ShareRecordFragment)
			.load(R.drawable.sharebackground_level01)
			.transform(CenterCrop(), RoundedCorners(roundedCorners))
			.into(object : CustomTarget<Drawable>() {
				override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable?>?) {
					binding.captureLayoutRecord.background = resource
				}

				override fun onLoadCleared(placeholder: Drawable?) {
				}
			})
	}
}