package com.example.viewbinding_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viewbinding_kotlin.databinding.ActivityMainBinding


//MainActivity.kt
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    //binding설정 (xml파일이름,공백제거,대문자+"Binding"이 붙음)
    //ex) activity_main -> AcitivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root) //setContentView는 binding.root로 꼭 넣어주기

        //ViewBinding을 활용한 예제
        //ex) "m_textview"의 id를 가진 textview에대한 clickListener
        binding.mTextview.setOnClickListener {
            binding.mTextview.text = "ViewBinding을 통한 TextView text 변경완료"
        }
    }
}