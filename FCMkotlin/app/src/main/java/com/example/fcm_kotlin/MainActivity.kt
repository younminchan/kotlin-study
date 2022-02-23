package com.example.fcm_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fcm_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //FCM
        var fb = MyFirebaseMessagingService()
        fb.getFirebaseToken() //FCM Token 가져오기

        //click

    }
}