package com.nexters.sticky.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nexters.sticky.data.entity.Sample

@Dao
interface SampleDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(sample: Sample): Long

	@Query("SELECT * FROM sample ORDER BY id ASC")
	fun fetch(): LiveData<MutableList<Sample>>

	@Query("SELECT * FROM sample WHERE id == :itemId")
	fun fetchOneItem(itemId: Long): LiveData<Sample>
}