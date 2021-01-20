package com.nexters.sticky.ui.dialog

import com.nexters.sticky.R
import com.nexters.sticky.base.BaseDialog
import com.nexters.sticky.databinding.DialogLayoutBinding

class ExitChallengeDialog : BaseDialog<DialogLayoutBinding>() {
	override fun getLayoutRes() = R.layout.dialog_layout

	override fun onStart() {
		super.onStart()

		initView()
		setOnClickListener()
	}

	private fun initView() {
		binding.apply {
			tvTitle.text = "챌린지 종료"
			tvDescription.text = "챌린지가 00때문에 종료되었습니다.\n최종 기록을 공유할까요?"
			tvButtonDoAction.text = "공유하기"
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