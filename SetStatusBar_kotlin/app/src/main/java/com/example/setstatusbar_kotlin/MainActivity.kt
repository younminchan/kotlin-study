package com.example.setstatusbar_kotlin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.WindowInsetsControllerCompat
import com.example.setstatusbar_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickinit()
    }

    /** onClick Listener */
    private fun onClickinit() {
        binding.tvBlack.setOnClickListener {
            setStatusBar("black")
        }

        binding.tvWhite.setOnClickListener {
            setStatusBar("white")
        }
    }

    /** 상태바 색상 변경 */
    private fun setStatusBar(barColor: String) {
        when (barColor) {
            "black" -> {
                window.apply {
                    //상태바
                    statusBarColor = Color.WHITE
                    //상태바 아이콘(true: 검정 / false: 흰색)
                    WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = true
                }
                Toast.makeText(baseContext, "설정 되었습니다.", Toast.LENGTH_SHORT).show()
            }
            "white" -> {
                window.apply {
                    //상태바
                    statusBarColor = Color.BLACK
                    //상태바 아이콘(true: 검정 / false: 흰색)
                    WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = false
                }
                Toast.makeText(baseContext, "설정 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}