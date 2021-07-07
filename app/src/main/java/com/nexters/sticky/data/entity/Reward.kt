package com.nexters.sticky.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reward")
data class Reward(
	@PrimaryKey(autoGenerate = true)
	val id: Long = 0L,
	val isBadge: Boolean = true,
	val receivedImageResId: Int = 0,
	val lockImageResId: Int = 0,
	val level: Int = -1,
	val description: String = "",
	val receivedConditionType: Int = RECEIVE.SPECIAL.ordinal,
	val hasReceived: Boolean = false,
	val receivedDate: Long = 0L
) {
	enum class RECEIVE {
		SPECIAL, MONTHLY, CONTINUOUS, LEVEL
	}
}
