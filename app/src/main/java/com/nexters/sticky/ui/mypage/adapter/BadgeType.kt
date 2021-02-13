package com.nexters.sticky.ui.mypage.adapter

import com.nexters.sticky.R

enum class BadgeType(val titleResId: Int, val descriptionResId: Int) {
	SPECIAL(R.string.my_page_special_title, R.string.my_page_special_description),
	MONTHLY(R.string.my_page_monthly_title, R.string.my_page_monthly_description),
	CONTINUOUS(R.string.my_page_continuous_title, R.string.my_page_continuous_description)
}