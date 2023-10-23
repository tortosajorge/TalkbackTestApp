package com.example.talkbacktestapp

import androidx.recyclerview.widget.RecyclerView
import com.example.talkbacktestapp.databinding.RvBinding

class ItemViewHolder(private val binding: RvBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Item) {
        binding.itemTitle.text = item.title
        binding.itemImage.setImageResource(R.drawable.ic_launcher_background)
        binding.itemContainer.contentDescription = item.title
    }
}