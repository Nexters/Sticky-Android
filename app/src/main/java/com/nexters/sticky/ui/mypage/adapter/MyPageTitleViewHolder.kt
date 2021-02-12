package com.nexters.sticky.ui.mypage.adapter

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.nexters.sticky.databinding.ItemMyPageTitleBinding

class MyPageTitleViewHolder(val binding: ItemMyPageTitleBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
	private var onClick: OnClickSetFilter? = null

	fun bind(item: Any) {
		if (item is BadgeType) {
			binding.apply {
				if (item == BadgeType.MONTHLY) setMonthlyFilter()

				line.isVisible = item != BadgeType.SPECIAL

				tvTitle.text = context.getText(item.titleResId)
				tvTitleDescription.text = context.getText(item.descriptionResId)
			}
		}
	}

	private fun setMonthlyFilter() {
		binding.btnFilter.apply {
			visibility = View.VISIBLE
			setOnClickListener {
				onClick?.onClickSetFilter()
			}
		}
	}

	fun setOnClickSetFilter(listener: () -> Unit) {
		onClick = object : OnClickSetFilter {
			override fun onClickSetFilter() {
				listener()
			}
		}
	}

	interface OnClickSetFilter {
		fun onClickSetFilter()
	}
}