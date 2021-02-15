package com.nexters.sticky.ui.main.adapter

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.nexters.sticky.databinding.ItemMainBadgeShowAllBinding
import com.nexters.sticky.ui.mypage.MyPageActivity

class MainShowAllViewHolder(val binding: ItemMainBadgeShowAllBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
	fun bind() {
		binding.root.setOnClickListener {
			val intent = Intent(context, MyPageActivity::class.java)
			context.startActivity(intent)
		}
	}
}