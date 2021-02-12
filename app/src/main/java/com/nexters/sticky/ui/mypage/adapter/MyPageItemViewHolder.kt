package com.nexters.sticky.ui.mypage.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nexters.sticky.databinding.ItemMyPageBadgeBinding
import timber.log.Timber

class MyPageItemViewHolder(val binding: ItemMyPageBadgeBinding) : RecyclerView.ViewHolder(binding.root) {
	fun bind(item: Any, isFiltered: Boolean) {
		if (item is MyPageBadge) {
			binding.apply {
				if (isFiltered) {
					tvBadgeCount.visibility = View.VISIBLE
					tvBadgeCount.text = if (item.badgeCount > 9) "9+" else item.badgeCount.toString()
				}

				imgBadge.setImageResource(item.badgeImage)
				tvBadgeName.text = item.badgeName
				tvReceivedDate.text = item.receivedDate

				root.setOnClickListener {
					// TODO : 뱃지 별 공유하기 연결
					Timber.d("공유하기")
				}
			}
		}
	}
}