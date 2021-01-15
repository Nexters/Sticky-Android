package com.nexters.sticky.ui.share

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ShareFragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){

	override fun getItemCount(): Int {
		return 3
	}

	override fun createFragment(position: Int): Fragment {
		return when(position) {
			0 -> ShareRecordFragment()
			1 -> ShareAccumFragment()
			else -> ShareBadgeFragment()
		}
	}
}