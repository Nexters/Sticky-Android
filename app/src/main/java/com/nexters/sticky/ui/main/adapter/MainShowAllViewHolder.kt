package com.nexters.sticky.ui.main.adapter

import androidx.recyclerview.widget.RecyclerView
import com.nexters.sticky.databinding.ItemMainBadgeShowAllBinding
import timber.log.Timber

class MainShowAllViewHolder(val binding: ItemMainBadgeShowAllBinding) : RecyclerView.ViewHolder(binding.root) {
	fun bind() {
		binding.root.setOnClickListener {
			Timber.d("show all badges")
		}
	}
}