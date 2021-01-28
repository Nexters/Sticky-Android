package com.nexters.sticky.ui.address

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivitySetAddressBinding
import com.nexters.sticky.ui.map.MapActivity

class SetAddressActivity : BaseActivity<ActivitySetAddressBinding>() {
	override val viewModel: SetAddressViewModel by viewModels()

	override val layoutRes = R.layout.activity_set_address
	override val actionBarLayoutRes = R.layout.actionbar_setaddress_layout
	override val statusBarColorRes = R.color.purple_200

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		//viewModel.setText("현재 기록")
		setActionBar()
		setOnClickListener()
	}

	private fun setActionBar() {
		actionBar.clickListener(R.id.back_main_btn) {
			onBackPressed()
		}
	}

	private fun setOnClickListener() {
		binding.findHereBtn.setOnClickListener() {
			val intent = Intent(this, MapActivity::class.java)
			startActivity(intent)

		}
	}
}