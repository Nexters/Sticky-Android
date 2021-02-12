package com.nexters.sticky.ui.mypage.adapter

data class MyPageBadge(
	val badgeImage: Int,
	val badgeName: String,
	val badgeCount: Int,
	val receivedDate: String,
	val badgeType: BadgeType? = null
)