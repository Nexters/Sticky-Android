package com.nexters.sticky.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.nexters.sticky.databinding.ItemMainBadgeBinding

class MainViewHolder(val binding: ItemMainBadgeBinding) : RecyclerView.ViewHolder(binding.root) {
	fun bind(item: BadgeToGet) {
		binding.apply {
			imgBadge.setImageResource(item.imageRes)
			tvBadge.text = "${item.badgeName} hours"
			tvRemainingTime.text = "${item.remainingTime} 남음"
		}
	}
}