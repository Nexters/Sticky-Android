package com.nexters.sticky.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nexters.sticky.data.dao.RewardDao
import com.nexters.sticky.data.entity.Reward

@Database(
	entities = [Reward::class],
	version = 1,
	exportSchema = false
)
abstract class StickyDatabase : RoomDatabase() {
	abstract fun rewardDao(): RewardDao
}