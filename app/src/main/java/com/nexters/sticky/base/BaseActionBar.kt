package com.nexters.sticky.base

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.nexters.sticky.R

class BaseActionBar {
	lateinit var activity: AppCompatActivity
	lateinit var view: View

	fun initActionBar(activity: AppCompatActivity, actionBarLayoutRes: Int) {
		this.activity = activity

		view = LayoutInflater.from(this.activity).inflate(actionBarLayoutRes, null)

		val params = ActionBar.LayoutParams(
			ActionBar.LayoutParams.MATCH_PARENT,
			ActionBar.LayoutParams.MATCH_PARENT)

		this.activity.supportActionBar?.let {
			it.setDisplayShowCustomEnabled(true)
			it.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
			it.setCustomView(view, params)
		}
	}

	fun setLayoutBackgroundColor(colorRes: Int) {
		val color = activity.getColor(colorRes)
		view.findViewById<ConstraintLayout>(R.id.actionbar_layout)?.setBackgroundColor(color)
	}

	fun clickListener(res: Int, listener: () -> Unit) {
		view.findViewById<ImageView>(res)?.let {
			it.setOnClickListener {
				listener()
			}
			it.visibility = View.VISIBLE
		}
	}

	fun setText(res: Int, text: String) {
		view.findViewById<TextView>(res)?.let {
			it.text = text
			it.visibility = View.VISIBLE
		}
	}

	fun setImage(res: Int, imageResId: Int) {
		view.findViewById<ImageView>(res)?.let {
			it.setImageResource(imageResId)
		}
	}

	fun setIsVisible(res: Int) {
		view.findViewById<ImageView>(res)?.let {
			it.isVisible = it.visibility != View.VISIBLE
		}
	}
}