package com.nexters.sticky.data.repository

import com.nexters.sticky.data.dao.RewardDao
import com.nexters.sticky.data.entity.Reward
import javax.inject.Inject

class RewardRepository @Inject constructor(private val rewardDao: RewardDao) {
	suspend fun insertReward(reward: Reward) {
		rewardDao.insertReward(reward)
	}

	suspend fun insertRewards(rewards: List<Reward>) {
		rewardDao.insertRewardList(rewards)
	}

	suspend fun updateRewardInfo(reward: Reward) {
		rewardDao.updateReward(reward)
	}

	fun fetchUserLevelInfo(level: Int) = rewardDao.fetchUserLevelInfo(level)
	fun fetchUserBadgeInfo(type: Int) = rewardDao.fetchUserBadgeInfo(type)
}