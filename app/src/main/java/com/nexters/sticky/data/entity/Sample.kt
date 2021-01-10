package com.nexters.sticky.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sample")
data class Sample(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String = ""
)
