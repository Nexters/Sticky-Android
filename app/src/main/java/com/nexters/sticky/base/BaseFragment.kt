package com.nexters.sticky.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
	abstract val viewModel: ViewModel
	abstract val layoutResId: Int

	protected val binding by lazy {
		DataBindingUtil.inflate(layoutInflater, layoutResId, container, false) as T
	}

	private lateinit var inflater: LayoutInflater
	private var container: ViewGroup? = null

	abstract fun setUpBinding()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		this.inflater = inflater
		this.container = container

		return binding.root
	}

	override fun onStart() {
		super.onStart()
		setUpBinding()
		binding.lifecycleOwner = this
	}

	fun toast(message: String) {
		activity?.let {
			Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
		}
	}
}