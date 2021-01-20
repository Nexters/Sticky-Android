package com.nexters.sticky.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.nexters.sticky.databinding.ItemMainBadgeBinding

class MainViewHolder(val binding: ItemMainBadgeBinding) : RecyclerView.ViewHolder(binding.root) {
	fun bind(item: BadgeToGet) {
		binding.apply {
			imgBadge.setImageResource(item.imageRes)
			tvRemainingTime.text = "${item.remainingTime} 시간 뒤에 받아요"
			tvAim.text = "${item.aim} 시간"
			progressBar.progress = item.progressPercentage
		}
	}
}