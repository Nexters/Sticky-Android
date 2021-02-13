package com.nexters.sticky.ui.mypage

import android.content.Context
import android.content.Intent
import com.nexters.sticky.R

enum class ViewMore(private val titleResId: Int, private val imageResId: Int) {
	ABOUT(R.string.view_more_about_sticky, R.drawable.ic_launcher_foreground),
	LEVEL(R.string.my_page_level_info, R.drawable.ic_launcher_background),
	LICENSE(R.string.view_more_open_source_licence, R.drawable.ic_image_logo);

	fun goToDetailActivity(context: Context) {
		val intent = Intent(context, ViewMoreDetailActivity::class.java).apply {
			putExtra("titleResId", titleResId)
			putExtra("imageResId", imageResId)
		}
		context.startActivity(intent)
	}
}