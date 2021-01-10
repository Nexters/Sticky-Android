package com.nexters.sticky.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("changeImage")
fun setImageResource(image: ImageView, src: Int) {
	image.setImageResource(src)
}