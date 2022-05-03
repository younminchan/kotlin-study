package com.example.animationtransition_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.animationtransition_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var rvAdapter: RvAdapter
    val datas = mutableListOf<RvData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvAdapter = RvAdapter(this)
        binding.rvUser.adapter = rvAdapter

        datas.apply {
            add(RvData("http://file.instiz.net/data/cached_img/upload/4/7/c/47cccd228b523f0f5881112abfcbbba4.png", "user1", "21"))
            add(RvData("http://file.instiz.net/data/cached_img/upload/0/c/e/0ce9fc47b33395509975f8e7f69f16b8.png", "user2", "22"))
            add(RvData("https://pub.cyphers.co.kr/images2/art/2013/05/27/1369646678644.png", "user3", "23"))
            add(RvData("https://pub.cyphers.co.kr/images2/art/2013/05/27/1369646724165.png", "user4", "24"))
            add(RvData("https://pub.cyphers.co.kr/images2/art/2013/05/27/1369646794990.jpg", "user5", "25"))
            add(RvData("https://pub.cyphers.co.kr/images2/art/2013/05/27/1369646804108.png", "user6", "26"))
            add(RvData("https://pub.cyphers.co.kr/images2/art/2013/05/27/1369646813408.png", "user7", "27"))
            add(RvData("https://pub.cyphers.co.kr/images2/art/2013/05/27/1369646818535.jpg", "user8", "28"))
            add(RvData("https://pub.cyphers.co.kr/images2/art/2013/05/27/1369646829912.png", "user9", "29"))
            add(RvData("https://pub.cyphers.co.kr/images2/art/2013/05/27/1369646850785.png", "user10", "30"))
        }

        rvAdapter.datas = datas
        rvAdapter.notifyDataSetChanged()
    }
}