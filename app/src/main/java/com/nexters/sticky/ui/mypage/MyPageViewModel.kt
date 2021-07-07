package com.nexters.sticky.ui.mypage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.sticky.R
import com.nexters.sticky.data.entity.Reward
import com.nexters.sticky.data.repository.RewardRepository
import kotlinx.coroutines.launch

class MyPageViewModel @ViewModelInject constructor(private val rewardRepository: RewardRepository) : ViewModel() {

	init {
		val initData = listOf(
			// LEVEL
			Reward(0, false, R.mipmap.blue_level_1, 0, 1, "Blue Sticky", Reward.RECEIVE.LEVEL.ordinal, false, 0L),
			Reward(1, false, R.mipmap.blue_level_2, 0, 2, "Blue Sticky", Reward.RECEIVE.LEVEL.ordinal, false, 0L),
			Reward(2, false, R.mipmap.blue_level_3, 0, 3, "Blue Sticky", Reward.RECEIVE.LEVEL.ordinal, false, 0L),
			Reward(3, false, R.mipmap.yellow_level_1, 0, 4, "YELLOW Sticky", Reward.RECEIVE.LEVEL.ordinal, false, 0L),
			Reward(4, false, R.mipmap.yellow_level_2, 0, 5, "YELLOW Sticky", Reward.RECEIVE.LEVEL.ordinal, false, 0L),
			Reward(5, false, R.mipmap.yellow_level_3, 0, 5, "YELLOW Sticky", Reward.RECEIVE.LEVEL.ordinal, false, 0L),
			Reward(6, false, R.mipmap.green_level_1, 0, 7, "GREEN Sticky", Reward.RECEIVE.LEVEL.ordinal, false, 0L),
			Reward(7, false, R.mipmap.green_level_2, 0, 8, "GREEN Sticky", Reward.RECEIVE.LEVEL.ordinal, false, 0L),
			Reward(8, false, R.mipmap.green_level_3, 0, 9, "GREEN Sticky", Reward.RECEIVE.LEVEL.ordinal, false, 0L),
			Reward(9, false, R.mipmap.red_level_1, 0, 10, "RED Sticky", Reward.RECEIVE.LEVEL.ordinal, false, 0L),

			// MONTHLY
			Reward(10, true, R.mipmap.monthly_badge_monthly_10_received, R.mipmap.monthly_badge_monthly_10_locked, -1, "", Reward.RECEIVE.MONTHLY.ordinal, false, 0L),
			Reward(11, true, R.mipmap.monthly_badge_monthly_30_received, R.mipmap.monthly_badge_monthly_30_locked, -1, "", Reward.RECEIVE.MONTHLY.ordinal, false, 0L),
			Reward(12, true, R.mipmap.monthly_badge_monthly_50_received, R.mipmap.monthly_badge_monthly_50_locked, -1, "", Reward.RECEIVE.MONTHLY.ordinal, false, 0L),
			Reward(13, true, R.mipmap.monthly_badge_monthly_100_received, R.mipmap.monthly_badge_monthly_100_locked, -1, "", Reward.RECEIVE.MONTHLY.ordinal, false, 0L),
			Reward(14, true, R.mipmap.monthly_badge_monthly_150_received, R.mipmap.monthly_badge_monthly_150_locked, -1, "", Reward.RECEIVE.MONTHLY.ordinal, false, 0L),
			Reward(15, true, R.mipmap.monthly_badge_monthly_300_received, R.mipmap.monthly_badge_monthly_300_locked, -1, "", Reward.RECEIVE.MONTHLY.ordinal, false, 0L),
			Reward(16, true, R.mipmap.monthly_badge_monthly_500_received, R.mipmap.monthly_badge_monthly_500_locked, -1, "", Reward.RECEIVE.MONTHLY.ordinal, false, 0L),
			Reward(17, true, R.mipmap.monthly_badge_monthly_700_received, R.mipmap.monthly_badge_monthly_700_locked, -1, "", Reward.RECEIVE.MONTHLY.ordinal, false, 0L),
			Reward(18, true, R.mipmap.monthly_badge_monthly_720_received, R.mipmap.monthly_badge_monthly_720_locked, -1, "", Reward.RECEIVE.MONTHLY.ordinal, false, 0L),

			// CONTINUOUS
			Reward(19, true, R.mipmap.nonstop_badge_nonstop_12_hours, R.mipmap.nonstop_badge_nonstop_12_hours_locked, -1, "", Reward.RECEIVE.CONTINUOUS.ordinal, false, 0L),
			Reward(20, true, R.mipmap.nonstop_badge_nonstop_1_days, R.mipmap.nonstop_badge_nonstop_1_day_locked, -1, "", Reward.RECEIVE.CONTINUOUS.ordinal, false, 0L),
			Reward(21, true, R.mipmap.nonstop_badge_nonstop_3_days, R.mipmap.nonstop_badge_nonstop_3_days_locked, -1, "", Reward.RECEIVE.CONTINUOUS.ordinal, false, 0L),
			Reward(22, true, R.mipmap.nonstop_badge_nonstop_7_days, R.mipmap.nonstop_badge_nonstop_10_days_locked, -1, "", Reward.RECEIVE.CONTINUOUS.ordinal, false, 0L),
			Reward(23, true, R.mipmap.nonstop_badge_nonstop_10_days, R.mipmap.nonstop_badge_nonstop_10_days_locked, -1, "", Reward.RECEIVE.CONTINUOUS.ordinal, false, 0L),
			Reward(24, true, R.mipmap.nonstop_badge_nonstop_15_days, R.mipmap.nonstop_badge_nonstop_10_days_locked, -1, "", Reward.RECEIVE.CONTINUOUS.ordinal, false, 0L),
			Reward(25, true, R.mipmap.nonstop_badge_nonstop_30_days, R.mipmap.nonstop_badge_nonstop_10_days_locked, -1, "", Reward.RECEIVE.CONTINUOUS.ordinal, false, 0L),

			// SPECIAL
			Reward(26, true, R.mipmap.spcial_badge, R.mipmap.special_badge_locked, -1, "", Reward.RECEIVE.SPECIAL.ordinal, false, 0L),
			Reward(27, true, R.mipmap.spcial_badge, R.mipmap.special_badge_locked, -1, "", Reward.RECEIVE.SPECIAL.ordinal, false, 0L),
			Reward(28, true, R.mipmap.spcial_badge, R.mipmap.special_badge_locked, -1, "", Reward.RECEIVE.SPECIAL.ordinal, false, 0L)
		)

		viewModelScope.launch {
			rewardRepository.insertRewards(initData)
		}
	}

	val levelInfo = rewardRepository.fetchUserLevelInfo(1)

}