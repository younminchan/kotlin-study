package com.example.securewindow_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.securewindow_kotlin.databinding.ActivityMainBinding

//MainActivity.kt
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tbSecure.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                SecureWindow(true)
            }else{
                SecureWindow(false)
            }
        }
    }

    /** 캡처방지(Android) 설정&해제 */
    fun SecureWindow(bool: Boolean){
        if(bool){
            window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
}