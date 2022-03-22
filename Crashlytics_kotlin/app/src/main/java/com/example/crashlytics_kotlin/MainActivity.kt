package com.example.crashlytics_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.crashlytics_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvException.setOnClickListener {
            throw RuntimeException("Test Crash")
        }
    }
}