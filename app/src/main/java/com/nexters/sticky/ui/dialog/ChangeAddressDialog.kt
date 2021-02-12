package com.nexters.sticky.ui.dialog

import android.content.Intent
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseDialog
import com.nexters.sticky.databinding.DialogLayoutBinding
import com.nexters.sticky.ui.address.SetAddressActivity
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

		binding.tvButtonDoAction.setBackgroundResource(R.drawable.dialog_button_background_negative)
	}

	private fun setOnClickListener() {
		binding.tvButtonDoAction.setOnClickListener {
			// TODO : 정보 초기화

			val intent = Intent(activity, SetAddressActivity::class.java)
			intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
			startActivity(intent)
		}

		binding.tvButtonCancel.setOnClickListener {
			dismiss()
		}
	}
}