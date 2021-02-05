package com.nexters.sticky.ui.address

import androidx.fragment.app.activityViewModels
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseFragment
import com.nexters.sticky.databinding.FragmentAddressResultBinding

class AddressResultFragment : BaseFragment<FragmentAddressResultBinding>() {
	override val viewModel: MapViewModel by activityViewModels()
	override val layoutResId = R.layout.fragment_address_result

	override fun setUpBinding() {
		binding.vm = viewModel
	}

}