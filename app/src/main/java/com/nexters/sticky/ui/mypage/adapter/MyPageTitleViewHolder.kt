package com.nexters.sticky.ui.mypage.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nexters.sticky.databinding.ItemMyPageTitleBinding
import timber.log.Timber

class MyPageTitleViewHolder(val binding: ItemMyPageTitleBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
	fun bind(item: Any) {
		if (item is BadgeType) {
			binding.apply {
				if (item == BadgeType.SPECIAL) line.visibility = View.GONE
				if (item == BadgeType.MONTHLY) setMonthlyFilter()

				tvTitle.text = context.getText(item.titleResId)
				tvTitleDescription.text = context.getText(item.descriptionResId)
			}
		}
	}

	private fun setMonthlyFilter() {
		binding.btnFilter.apply {
			visibility = View.VISIBLE
			setOnClickListener {
				// TODO : 월간기록 badge Count
				Timber.d("set Monthly filter")
			}
		}
	}
}