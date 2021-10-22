package com.example.recyclerview_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview_kotlin.databinding.ActivityMainBinding

//MainActivity.kt
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dataset: MutableList<rv_item> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //바인딩 초기화
        binding = ActivityMainBinding.inflate(layoutInflater);

        //레이아웃(root뷰) 표시
        setContentView(binding.root);

        addData()

        binding.rvRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvRecyclerview.adapter = RvAdapter(dataset)

    }

    private fun addData() {
        for (i in 0..99) {
            dataset.add(rv_item("$i th Name", "$i th Num"))
        }
    }
}