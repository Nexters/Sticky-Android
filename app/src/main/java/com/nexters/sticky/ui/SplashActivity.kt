package com.nexters.sticky.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.nexters.sticky.R
import com.nexters.sticky.ui.address.SetAddressActivity
import com.nexters.sticky.ui.gps.NeedPermissionActivity
import com.nexters.sticky.utils.LOCATION_PERMISSION_REQUEST_CODE
import com.nexters.sticky.utils.checkLocationPermission
import com.nexters.sticky.utils.requestLocationPermission
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

	private val splashDelay = 1000L

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)

		window.statusBarColor = ContextCompat.getColor(this, R.color.primary_purple)

		lifecycleScope.launch {
			delay(splashDelay)
			checkPermission()
		}
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
		when (requestCode) {
			LOCATION_PERMISSION_REQUEST_CODE -> {
				if (grantResults.isEmpty() || grantResults.contains(PackageManager.PERMISSION_DENIED)) {
					goToNeedPermissionActivity()
				} else {
					goToMainActivity()
				}
			}
		}
	}

	private fun checkPermission() {
		when {
			!checkLocationPermission() -> requestLocationPermission()
			else -> goToMainActivity()
		}
	}

	private fun goToNeedPermissionActivity() {
		val intent = Intent(this@SplashActivity, NeedPermissionActivity::class.java).apply {
			addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
		}
		startActivity(intent)
		finish()
	}

	private fun goToMainActivity() {
		val intent = Intent(this@SplashActivity, SetAddressActivity::class.java)
		startActivity(intent)
		finish()
	}
}