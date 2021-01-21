package com.nexters.sticky.ui.dialog

import com.nexters.sticky.R
import com.nexters.sticky.base.BaseDialog
import com.nexters.sticky.databinding.DialogLayoutBinding
import com.nexters.sticky.ui.dialog.DialogType.CHANGE_ADDRESS

class ChangeAddressDialog : BaseDialog<DialogLayoutBinding>() {
	override fun getLayoutRes() = R.layout.dialog_layout

	override fun onStart() {
		super.onStart()

		initView()
		setOnClickListener()
	}

	private fun initView() {
		context?.let {
			binding.tvTitle.text = it.resources.getText(CHANGE_ADDRESS.titleRes)
			binding.tvDescription.text = it.resources.getText(CHANGE_ADDRESS.descriptionRes)
			binding.tvButtonDoAction.text = it.resources.getText(CHANGE_ADDRESS.doActionRes)
		}
	}

	private fun setOnClickListener() {
		binding.tvButtonDoAction.setOnClickListener {
			toast("do something..")
			dismiss()
		}

		binding.tvButtonCancel.setOnClickListener {
			toast("dismiss dialog")
			dismiss()
		}
	}
}