package com.example.signaturepad_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.signaturepad_kotlin.databinding.ActivityMainBinding
import com.github.gcacace.signaturepad.views.SignaturePad

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signaturePad.setOnSignedListener(object: SignaturePad.OnSignedListener{
            override fun onStartSigning() {
                Log.e("YMC", "onStartSigning")
            }

            override fun onSigned() {
                Log.e("YMC", "onSigned")
            }

            override fun onClear() {
                Log.e("YMC", "onClear")
            }

        })

    }
}