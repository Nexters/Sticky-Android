package com.nexters.sticky.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.nexters.sticky.databinding.BaseToolbarLayoutBinding

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
	abstract val viewModel: ViewModel

	abstract val layoutRes: Int
	abstract val actionBarLayoutRes: Int
	abstract val statusBarColorRes: Int

	lateinit var baseBinding: BaseToolbarLayoutBinding
	lateinit var binding: T

	protected val actionBar = BaseActionBar()

	abstract fun setUpBinding()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		baseBinding = BaseToolbarLayoutBinding.inflate(layoutInflater)
		setContentView(baseBinding.root)

		setUpContainerBinding()
		initView()
	}

	private fun setUpContainerBinding() {
		binding = DataBindingUtil.inflate(layoutInflater, layoutRes, baseBinding.baseContainer, true)
		binding.lifecycleOwner = this
		setUpBinding()
	}

	private fun initView() {
		setStatusBarColor()
		setActionBar()
	}

	private fun setStatusBarColor() {
		window.statusBarColor = ContextCompat.getColor(this, statusBarColorRes)
	}

	private fun setActionBar() {
		setSupportActionBar(baseBinding.toolbar)
		actionBar.initActionBar(this, actionBarLayoutRes)
		actionBar.setLayoutBackgroundColor(statusBarColorRes)
	}

	fun toast(message: String) {
		Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
	}
}