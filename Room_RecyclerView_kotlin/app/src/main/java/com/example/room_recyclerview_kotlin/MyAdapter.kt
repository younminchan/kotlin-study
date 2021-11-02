package com.example.room_recyclerview_kotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.room_recyclerview_kotlin.databinding.LayoutItemBinding

class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var userList = emptyList<User>()

    class MyViewHolder(val binding: LayoutItemBinding) : RecyclerView.ViewHolder(binding.root)

    //xml 어떤것으로 ViewHolder 생성할지 결정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LayoutItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    //ViewHolder에 데이터를 바인딩
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.binding.idText.text = currentItem.id.toString()
        holder.binding.nameText.text = currentItem.name
        holder.binding.ageText.text = currentItem.age.toString()
        //text지정 하지 않고 databinding으로 처리할 순 없는지?
    }

    //ViewHolder 개수 리턴
    override fun getItemCount(): Int {
        return userList.size
    }

    //UserList 갱신
    fun setData(user: List<User>) {
        userList = user
        notifyDataSetChanged()
    }
}