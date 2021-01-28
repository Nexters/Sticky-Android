package com.nexters.sticky.ui.gps

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.nexters.sticky.R
import com.nexters.sticky.databinding.ActivityGetGpsPermissionBinding
import com.nexters.sticky.ui.address.SetAddressActivity
import com.nexters.sticky.ui.map.MapActivity
import com.nexters.sticky.utils.showAnimation

class GetGpsPermissionActivity : AppCompatActivity() {

	lateinit var binding: ActivityGetGpsPermissionBinding

	private val animationDuration = 1000L
	private val animationDistance = 100F
	private val animationTranslationY = "translationY"

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityGetGpsPermissionBinding.inflate(layoutInflater)
		setContentView(binding.root)

		window.statusBarColor = ContextCompat.getColor(this, R.color.brand_color)

		setAnimation()
		goToMain() // 개발 편의를 위해 임시로 작성 - 로고 클릭 시 메인으로 이동
	}

	private fun goToMain() {
		binding.imgLogo.setOnClickListener {
			val intent = Intent(this, SetAddressActivity::class.java)
			startActivity(intent)
		}
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