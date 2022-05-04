package com.example.animationtransition_kotlin

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animationtransition_kotlin.databinding.RvItemBinding
import android.util.*

class RvAdapter(private val context: Context, private val act: MainActivity) : RecyclerView.Adapter<RvAdapter.ViewHolder>() {
    var datas = mutableListOf<RvData>()

    class ViewHolder(val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false)
        return ViewHolder(RvItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(datas[position].img).into(holder.binding.imgRvPhoto)
        holder.binding.tvRvAge.text = datas[position].age
        holder.binding.tvRvName.text = datas[position].name

        holder.binding.root.setOnClickListener {
            Intent(context, UserDetailActivity::class.java).apply {
                putExtra("data", datas[position])
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.run {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    val pair1 = Pair<View, String>(holder.binding.imgRvPhoto, "image")
                    val pair2 = Pair<View, String>(holder.binding.tvRvName, "name")

                    val options = ActivityOptions.makeSceneTransitionAnimation(act, pair1, pair2)
                    context.startActivity(this, options.toBundle())
                }else{
                    context.startActivity(this)
                }
            }
        }
    }

    override fun getItemCount(): Int = datas.size

}