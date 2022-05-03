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

        var img = intent.getStringExtra("img")
        var name = intent.getStringExtra("name")

        Glide.with(this).load(img).into(binding.imgProfile)
        binding.tvName.text = name
    }
}