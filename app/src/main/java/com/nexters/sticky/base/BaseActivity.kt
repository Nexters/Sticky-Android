package com.nexters.sticky.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
	abstract val viewModel: ViewModel
	abstract val getLayoutRes: Int

	protected val binding by lazy {
		DataBindingUtil.setContentView(this, getLayoutRes) as T
	}

	abstract fun setUpBinding()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setUpBinding()
		binding.lifecycleOwner = this
	}

	fun toast(message: String) {
		Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
	}
}