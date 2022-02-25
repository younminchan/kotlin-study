package com.example.fcm_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fcm_kotlin.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}