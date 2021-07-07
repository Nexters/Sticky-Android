package com.nexters.sticky.di

import android.content.Context
import androidx.room.Room
import com.nexters.sticky.data.StickyDatabase
import com.nexters.sticky.data.dao.RewardDao
import com.nexters.sticky.data.repository.RewardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
	@Provides
	@Singleton
	fun provideRoomDataBase(@ApplicationContext context: Context): StickyDatabase {
		return Room.databaseBuilder(context, StickyDatabase::class.java, "reward_db").build()
	}

	@Provides
	fun providesRewardDao(roomDatabase: StickyDatabase): RewardDao {
		return roomDatabase.rewardDao()
	}

	@Provides
	fun providesRewardRepository(rewardDao: RewardDao) = RewardRepository(rewardDao)
}