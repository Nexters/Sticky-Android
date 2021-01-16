package com.nexters.sticky.ui.share

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseFragment
import com.nexters.sticky.databinding.FragmentShareBadgeBinding

class ShareBadgeFragment : BaseFragment<FragmentShareBadgeBinding>() {
	override val viewModel: ShareViewModel by viewModels()
	override val layoutResId = R.layout.fragment_share_badge

	override fun setUpBinding(){
		binding.vm = viewModel
	}
}
