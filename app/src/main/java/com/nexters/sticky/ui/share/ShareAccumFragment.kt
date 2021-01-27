package com.nexters.sticky.ui.share

import androidx.fragment.app.viewModels
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseFragment
import com.nexters.sticky.databinding.FragmentShareAccumBinding

class ShareAccumFragment : BaseFragment<FragmentShareAccumBinding>() {
	override val viewModel: ShareViewModel by viewModels()
	override val layoutResId = R.layout.fragment_share_accum

	override fun setUpBinding() {
		binding.vm = viewModel
	}

}