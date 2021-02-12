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
		MyPageBadge(R.drawable.ic_launcher_background, "10Hours", 10, "101010"),
		MyPageBadge(R.drawable.ic_launcher_background, "30Hours", 2, "111111"),
		MyPageBadge(R.drawable.ic_launcher_background, "50Hours", 3, "322222"),
		BadgeType.MONTHLY,
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 4, "55555"),
		MyPageBadge(R.drawable.ic_launcher_background, "10Hours", 1, "101010"),
		MyPageBadge(R.drawable.ic_launcher_background, "30Hours", 2, "111111"),
		MyPageBadge(R.drawable.ic_launcher_background, "50Hours", 3, "322222"),
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 4, "55555"),
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 4, "55555"),
		MyPageBadge(R.drawable.ic_launcher_background, "10Hours", 1, "101010"),
		MyPageBadge(R.drawable.ic_launcher_background, "30Hours", 2, "111111"),
		MyPageBadge(R.drawable.ic_launcher_background, "50Hours", 3, "322222"),
		BadgeType.CONTINUOUS,
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 4, "55555"),
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 4, "55555"),
		MyPageBadge(R.drawable.ic_launcher_background, "10Hours", 1, "101010"),
		MyPageBadge(R.drawable.ic_launcher_background, "30Hours", 2, "111111"),
		MyPageBadge(R.drawable.ic_launcher_background, "50Hours", 3, "322222"),
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 4, "55555"),
		MyPageBadge(R.drawable.ic_launcher_background, "100Hours", 4, "55555"),
		MyPageBadge(R.drawable.ic_launcher_background, "10Hours", 1, "101010"),
		MyPageBadge(R.drawable.ic_launcher_background, "30Hours", 2, "111111"),
	)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val bindingTitle = ItemMyPageTitleBinding.inflate(LayoutInflater.from(parent.context))
		val bindingItem = ItemMyPageBadgeBinding.inflate(LayoutInflater.from(parent.context))
		return when (viewType) {
			BADGE.TITLE.ordinal -> MyPageTitleViewHolder(bindingTitle, parent.context)
			else -> MyPageItemViewHolder(bindingItem)
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		when (holder) {
			is MyPageTitleViewHolder -> holder.bind(sample[position])
			is MyPageItemViewHolder -> holder.bind(sample[position], showBadgeCount())
		}
	}

	override fun getItemCount() = sample.size

	override fun getItemViewType(position: Int): Int {
		return when (sample[position]) {
			is BadgeType -> BADGE.TITLE.ordinal
			else -> BADGE.ITEM.ordinal
		}
	}

	private fun showBadgeCount(): Boolean {
		return true
	}
}