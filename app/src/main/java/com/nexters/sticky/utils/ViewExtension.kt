package com.nexters.sticky.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

internal fun View.hideKeyboard() {
	val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.convertFloatToDp(value: Float): Int {
	return TypedValue.applyDimension(
		TypedValue.COMPLEX_UNIT_DIP,
		value,
		resources.displayMetrics
	).toInt()
}

fun View.showAnimation() {
	alpha = 0.0F

	animate()
		.translationY(height.toFloat())
		.alpha(1.0f)
		.setStartDelay(1000L)
		.setListener(null).duration = 1000L
}