package com.nexters.sticky.ui.mypage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nexters.sticky.R
import com.nexters.sticky.databinding.ItemMyPageBadgeBinding
import com.nexters.sticky.databinding.ItemMyPageTitleBinding

class MyPageAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
	enum class BADGE {
		TITLE, ITEM
	}

	private val sample = listOf(
		BadgeType.SPECIAL,
		MyPageBadge(R.drawable.ic_launcher_background, "10Hours", 1, "101010"),
		MyPageBadge(R.drawable.ic_launcher_background, "30Hours", 2, "111111"),
		MyPageBadge(R.drawable.ic_launcher_background, "50Hours", 3, "322222"),
		BadgeType.MONTHLY,
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 4, "55555", BadgeType.MONTHLY),
		MyPageBadge(R.drawable.ic_launcher_background, "10Hours", 5, "101010", BadgeType.MONTHLY),
		MyPageBadge(R.drawable.ic_launcher_background, "30Hours", 6, "111111", BadgeType.MONTHLY),
		MyPageBadge(R.drawable.ic_launcher_background, "50Hours", 7, "322222", BadgeType.MONTHLY),
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 8, "55555", BadgeType.MONTHLY),
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 9, "55555", BadgeType.MONTHLY),
		MyPageBadge(R.drawable.ic_launcher_background, "10Hours", 10, "101010", BadgeType.MONTHLY),
		MyPageBadge(R.drawable.ic_launcher_background, "30Hours", 11, "111111", BadgeType.MONTHLY),
		MyPageBadge(R.drawable.ic_launcher_background, "50Hours", 12, "322222", BadgeType.MONTHLY),
		BadgeType.CONTINUOUS,
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 13, "55555"),
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 14, "55555"),
		MyPageBadge(R.drawable.ic_launcher_background, "10Hours", 15, "101010"),
		MyPageBadge(R.drawable.ic_launcher_background, "30Hours", 16, "111111"),
		MyPageBadge(R.drawable.ic_launcher_background, "50Hours", 17, "322222"),
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 18, "55555"),
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 19, "55555"),
		MyPageBadge(R.drawable.ic_launcher_background, "10Hours", 20, "101010"),
		MyPageBadge(R.drawable.ic_launcher_background, "30Hours", 21, "111111"),
	)

	var isFiltered = false

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val bindingTitle = ItemMyPageTitleBinding.inflate(LayoutInflater.from(parent.context))
		val bindingItem = ItemMyPageBadgeBinding.inflate(LayoutInflater.from(parent.context))

		return when (viewType) {
			BADGE.TITLE.ordinal -> MyPageTitleViewHolder(bindingTitle, parent.context).apply {
				setOnClickSetFilter {
					isFiltered = !isFiltered
					notifyDataSetChanged()
				}
			}
			else -> MyPageItemViewHolder(bindingItem)
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (holder) {
			is MyPageTitleViewHolder -> holder.bind(sample[position])
			is MyPageItemViewHolder -> holder.bind(sample[position], showBadgeCount(position))
		}
	}

	override fun getItemCount() = sample.size

	override fun getItemViewType(position: Int): Int {
		return when (sample[position]) {
			is BadgeType -> BADGE.TITLE.ordinal
			else -> BADGE.ITEM.ordinal
		}
	}

	private fun showBadgeCount(position: Int): Boolean {
		if (sample[position] is MyPageBadge) {
			if (isFiltered && (sample[position] as MyPageBadge).badgeType == BadgeType.MONTHLY) {
				return true
			}
		}
		return false
	}
}