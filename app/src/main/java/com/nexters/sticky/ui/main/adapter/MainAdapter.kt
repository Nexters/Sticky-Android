package com.nexters.sticky.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexters.sticky.R
import com.nexters.sticky.databinding.ItemMainBadgeBinding
import com.nexters.sticky.databinding.ItemMainBadgeShowAllBinding

class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
	enum class TYPE {
		BADGE, SHOW_ALL
	}

	private val sample = listOf(
		BadgeToGet(R.mipmap.monthly_badge_monthly_10_locked, 3, 100),
		BadgeToGet(R.mipmap.monthly_badge_monthly_30_locked, 6, 12)
	)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val mainBinding = ItemMainBadgeBinding.inflate(LayoutInflater.from(parent.context))
		val showAllBinding = ItemMainBadgeShowAllBinding.inflate(LayoutInflater.from(parent.context))
		return when (viewType) {
			TYPE.BADGE.ordinal -> MainViewHolder(mainBinding)
			else -> MainShowAllViewHolder(showAllBinding)
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (holder) {
			is MainViewHolder -> holder.bind(sample[position])
			is MainShowAllViewHolder -> holder.bind()
		}
	}

	override fun getItemCount(): Int = sample.size + 1

	override fun getItemViewType(position: Int): Int {
		return when (position) {
			itemCount - 1 -> TYPE.SHOW_ALL.ordinal
			else -> TYPE.BADGE.ordinal
		}
	}
}