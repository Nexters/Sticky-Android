package com.nexters.sticky.ui.share

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseFragment
import com.nexters.sticky.databinding.FragmentShareBadgeBinding
import java.io.File
import java.io.FileOutputStream

class ShareBadgeFragment : BaseFragment<FragmentShareBadgeBinding>() {
	override val viewModel: ShareViewModel by viewModels()
	override val layoutResId = R.layout.fragment_share_badge

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		getCaptureScreen()
	}

	private fun getCaptureScreen() {
		val storage: String = context?.cacheDir.toString()
		val container = binding.captureLayoutBadge

		container.viewTreeObserver.addOnGlobalLayoutListener {
			container.buildDrawingCache()
			val captureView = container.drawingCache
			val filename = "capturebadge.jpg"
			val tempFile = File(storage, filename)

			try {
				tempFile.createNewFile()
				val fos = FileOutputStream(tempFile)
				captureView?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}


	}


}
