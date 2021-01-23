package com.nexters.sticky.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.nexters.sticky.R
import com.nexters.sticky.ui.gps.GetGpsPermissionActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash)

		window.statusBarColor = ContextCompat.getColor(this, R.color.brand_color)

		GlobalScope.launch {
			delay(1000L)
			checkGpsPermission()
		}
	}

	private fun checkGpsPermission() {
		val intent = Intent(this@SplashActivity, GetGpsPermissionActivity::class.java).apply {
			addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
		}
		startActivity(intent)
		finish()
	}
}