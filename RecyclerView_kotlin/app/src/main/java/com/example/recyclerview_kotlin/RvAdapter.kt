package com.example.recyclerview_kotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview_kotlin.databinding.RvItemRowBinding


//RvAdapter.kt
class RvAdapter(private val items: MutableList<rv_item>) :
    RecyclerView.Adapter<RvAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvAdapter.Holder {
        val binding = RvItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: RvAdapter.Holder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class Holder(private val binding: RvItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: rv_item) {
            binding.tvName.text = "Name: ${data.name}"
            binding.tvNum.text = "Num: ${data.num}"
        }
    }
}