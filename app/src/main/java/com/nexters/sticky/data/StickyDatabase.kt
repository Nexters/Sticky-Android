package com.nexters.sticky.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nexters.sticky.data.dao.SampleDao
import com.nexters.sticky.data.entity.Sample

@Database(
    entities = [Sample::class],
    version = 1,
    exportSchema = false
)
abstract class StickyDatabase : RoomDatabase() {
	abstract fun sampleDao(): SampleDao
}