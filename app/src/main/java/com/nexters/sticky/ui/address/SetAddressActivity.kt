package com.nexters.sticky.ui.address

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.viewModels
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivitySetAddressBinding
import java.io.IOException

class SetAddressActivity : BaseActivity<ActivitySetAddressBinding>() {
	override val viewModel: MapViewModel by viewModels()

	override val layoutRes = R.layout.activity_set_address
	override val actionBarLayoutRes = R.layout.actionbar_setaddress_layout
	override val statusBarColorRes = R.color.purple_200
	lateinit var geocoder: Geocoder
	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		//viewModel.setText("현재 기록")
		setActionBar()
		setOnClickListener()
		viewModel.setGuideText(getText(R.string.address_guide_txt).toString())
		viewModel.setAddressText(getText(R.string.address_tip_txt).toString())
	}

	private fun setActionBar() {
		actionBar.clickListener(R.id.back_main_btn) {
			onBackPressed()
		}
	}

	private fun setOnClickListener() {
		binding.findHereBtn.setOnClickListener {
			val intent = Intent(this, MapActivity::class.java)
			startActivity(intent)
		}
		binding.searchBtn.setOnClickListener {
			geocoder = Geocoder(this)
			val address = binding.addressEdittxt.text.toString()
			val addressList: List<Address>

			try {
				addressList = geocoder.getFromLocationName(address, 10)
				if (addressList.isNotEmpty()) {
					val splitStr = addressList[0].toString().split(",")
					viewModel.setAddressText(splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length - 2))
					//viewModel.setAddressText(splitStr.toString())
					viewModel.removeGuideText()
				} else {
					viewModel.setAddressText("주소를 찾을 수 없음")
				}

			} catch (e: IOException) {
				e.printStackTrace()
			}

		}
	}
}