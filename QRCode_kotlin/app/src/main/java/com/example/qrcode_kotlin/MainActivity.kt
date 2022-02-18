package com.example.qrcode_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.qrcode_kotlin.databinding.ActivityMainBinding

//MainActivity.kt
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var qrCodeScan = QRCodeScan(this)

        /** Click */
        binding.tvQrScan.setOnClickListener {
            qrCodeScan.startQRScan()
        }
    }
}