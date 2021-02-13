package com.nexters.sticky.ui.gps

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.nexters.sticky.R
import com.nexters.sticky.databinding.ActivityGetGpsPermissionBinding
import com.nexters.sticky.ui.address.SetAddressActivity
import com.nexters.sticky.utils.showAnimation

class NeedPermissionActivity : AppCompatActivity() {

	lateinit var binding: ActivityGetGpsPermissionBinding

	private val animationDuration = 1000L
	private val animationDistance = 100F
	private val animationTranslationY = "translationY"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityGetGpsPermissionBinding.inflate(layoutInflater)
		setContentView(binding.root)

		window.statusBarColor = ContextCompat.getColor(this, R.color.primary_purple)

		setAnimation()

		binding.tvGoToGpsSettings.setOnClickListener {
			goToSettings()
		}
	}

	private fun goToMain() {
		binding.imgLogo.setOnClickListener {
			val intent = Intent(this, SetAddressActivity::class.java)
			startActivity(intent)
		}
	}
	private fun goToSettings() {
		val packageName = Uri.fromParts("package", packageName, null)
		val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
			addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			data = packageName
		}
		startActivity(intent)
		finish()
	}

	private fun setAnimation() {
		logoAnimation()
		binding.tvDescription.showAnimation()
		binding.tvGoToGpsSettings.showAnimation()
	}

	private fun logoAnimation() {
		val moveDistance = TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP,
			animationDistance,
			binding.root.resources.displayMetrics
		)

		val animation = ObjectAnimator.ofFloat(binding.imgLogo, animationTranslationY, -moveDistance)
		animation.duration = animationDuration
		animation.start()
	}
}