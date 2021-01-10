package com.nexters.sticky.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nexters.sticky.StickyApplication
import com.nexters.sticky.data.StickyDatabase
import com.nexters.sticky.data.dao.SampleDao
import com.nexters.sticky.data.repository.SampleRepository
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
		return Room.databaseBuilder(context, StickyDatabase::class.java, "sample_db")
			.build()
	}

	@Provides
	@Singleton
	fun providesSampleDao(roomDatabase: StickyDatabase): SampleDao {
		return roomDatabase.sampleDao()
	}

	@Provides
	@Singleton
	fun providesSampleRepository(sampleDao: SampleDao) = SampleRepository(sampleDao)
}