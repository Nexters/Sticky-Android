package com.nexters.sticky.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialog<T : ViewDataBinding> : DialogFragment() {
	protected val binding by lazy {
		DataBindingUtil.inflate(inflater, getLayoutRes(), container, false) as T
	}

	private lateinit var inflater: LayoutInflater
	private var container: ViewGroup? = null

	abstract fun getLayoutRes(): Int

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		this.inflater = inflater
		this.container = container

		return binding.root
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		binding.lifecycleOwner = this

		val width = WindowManager.LayoutParams.WRAP_CONTENT
		val height = WindowManager.LayoutParams.WRAP_CONTENT

		dialog?.window?.setLayout(width, height)
		dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
	}

	fun toast(content: String) {
		activity?.let {
			Toast.makeText(it, content, Toast.LENGTH_SHORT).show()
		}
	}
}
