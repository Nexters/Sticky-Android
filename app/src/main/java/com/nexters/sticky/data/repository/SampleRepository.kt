package com.nexters.sticky.data.repository

import com.nexters.sticky.data.dao.SampleDao
import com.nexters.sticky.data.entity.Sample
import javax.inject.Inject

class SampleRepository @Inject constructor(private val sampleDao: SampleDao) {
    suspend fun insertData(sample: Sample) = sampleDao.insert(sample)
    fun fetchData() = sampleDao.fetch()
    fun fetchOne(id: Long) = sampleDao.fetchOneItem(id)
}