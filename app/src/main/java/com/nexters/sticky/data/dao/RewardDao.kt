package com.nexters.sticky.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nexters.sticky.data.entity.Reward

@Dao
interface RewardDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertReward(reward: Reward)

	@Update
	fun updateReward(reward: Reward)

	@Query("SELECT * FROM reward WHERE level = :userLevel")
	fun fetchUserLevelInfo(userLevel: Int): LiveData<List<Reward>>

	@Query("SELECT * FROM reward WHERE hasReceived = 1 AND receivedConditionType = :type")
	fun fetchUserBadgeInfo(type: Int): LiveData<List<Reward>>
}