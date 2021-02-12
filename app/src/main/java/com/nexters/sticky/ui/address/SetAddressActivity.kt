package com.nexters.sticky.ui.address

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.nexters.sticky.BuildConfig
import com.nexters.sticky.R
import com.nexters.sticky.base.BaseActivity
import com.nexters.sticky.databinding.ActivitySetAddressBinding
import com.nexters.sticky.ui.address.PlacesAutoCompleteAdapter.ClickListener

class SetAddressActivity : BaseActivity<ActivitySetAddressBinding>(), ClickListener {
	override val viewModel: MapViewModel by viewModels()

	override val layoutRes = R.layout.activity_set_address
	override val actionBarLayoutRes = R.layout.actionbar_setaddress_layout
	override val statusBarColorRes = R.color.white
	private lateinit var recyclerView: RecyclerView
	private lateinit var AutocompleteAdapter: PlacesAutoCompleteAdapter

	override fun setUpBinding() {
		binding.vm = viewModel
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		//viewModel.setText("현재 기록")
		setActionBar()
		setOnClickListener()

		val apiKey = BuildConfig.MAPS_API_KEY
		if (!Places.isInitialized()) {
			Places.initialize(applicationContext, apiKey)
		}
		val placesClient = Places.createClient(this)
		recyclerView = binding.placeRecyclerView
		AutocompleteAdapter = PlacesAutoCompleteAdapter(this)
		recyclerView.layoutManager = LinearLayoutManager(this)
		AutocompleteAdapter.setClickListener(this)
		recyclerView.adapter = AutocompleteAdapter
		AutocompleteAdapter.notifyDataSetChanged()
	}

	private fun setActionBar() {
		actionBar.clickListener(R.id.back_main_btn) {
			onBackPressed()
		}
	}

	private fun setOnClickListener() {
		binding.findHereBtn.setOnClickListener {
			val intent = Intent(this, MapActivity::class.java)
			intent.putExtra("findhere", true)
			startActivity(intent)
		}

		binding.searchBtn.setOnClickListener {
			val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
			imm.hideSoftInputFromWindow(binding.addressEdittxt.windowToken, 0)
		}

		binding.searchCloseBtn.setOnClickListener {
			binding.addressEdittxt.setText("")
		}

		binding.addressEdittxt.addTextChangedListener(filterTextWatcher)

	}

	private val filterTextWatcher: TextWatcher = object : TextWatcher {
		override fun afterTextChanged(s: Editable) {
			if (s.toString() != "") {
				AutocompleteAdapter.filter.filter(s.toString())
				if (recyclerView.visibility == View.GONE) {
					recyclerView.visibility = View.VISIBLE
				}
			} else {
				if (recyclerView.visibility == View.VISIBLE) {
					recyclerView.visibility = View.GONE
					binding.searchCloseBtn.visibility = View.GONE
					binding.searchGuide.visibility = View.VISIBLE
					binding.tipTxt.visibility = View.VISIBLE
				}
			}
		}

		override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
		}

		override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
			binding.searchGuide.visibility = View.GONE
			binding.tipTxt.visibility = View.GONE
			binding.searchCloseBtn.visibility = View.VISIBLE
		}
	}

	override fun click(place: Place) {
		val intent = Intent(this, MapActivity::class.java)
		intent.putExtra("name", place.name.toString())
		intent.putExtra("latitude", place.latLng!!.latitude)
		intent.putExtra("longtitude", place.latLng!!.longitude)
		intent.putExtra("address", place.address.toString())
		startActivity(intent)
	}
}