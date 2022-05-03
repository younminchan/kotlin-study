package com.example.animationtransition_kotlin

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animationtransition_kotlin.databinding.RvItemBinding

class RvAdapter(private val context: Context) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    var datas = mutableListOf<RvData>()

    class ViewHolder(val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(RvItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(datas[position].img).into(holder.binding.imgRvPhoto)
        Log.e("YMC", "img url: ${datas[position].img}")
        holder.binding.tvRvAge.text = datas[position].age
        holder.binding.tvRvName.text = datas[position].name
    }

    override fun getItemCount(): Int = datas.size

}