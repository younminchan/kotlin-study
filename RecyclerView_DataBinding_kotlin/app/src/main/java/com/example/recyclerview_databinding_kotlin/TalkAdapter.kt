package com.example.recyclerview_databinding_kotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview_databinding_kotlin.databinding.TalkItemRowBinding

class TalkAdapter : RecyclerView.Adapter<TalkAdapter.TalkHolder>() {
    private lateinit var talkBalloonBinding: TalkItemRowBinding
    val list = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalkHolder {
//        Adatper에서 바인딩.inflate() 방법1
//        talkBalloonBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.talk_item_row, parent, false)

//        Adatper에서 바인딩.inflate() 방법2
        talkBalloonBinding = TalkItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TalkHolder(talkBalloonBinding)
    }

    override fun onBindViewHolder(holder: TalkHolder, position: Int) {
        if (holder is TalkHolder) {
            holder.binding.tvLeftTalk.text = list[position]
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class TalkHolder(val binding: TalkItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String){
//            binding.
        }
    }

    fun addItem(s: String) {
        list.add(s)
    }
}