package com.example.animationtransition_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.animationtransition_kotlin.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.getSerializableExtra("data") as RvData

        Glide.with(this).load(data.img).into(binding.imgProfile)
        binding.tvName.text = data.name
    }
}