package com.example.glidecache

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.glidecache.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var ivArr = listOf<String>(
        "https://windowsforum.kr/files/attach/images/48/938/503/013/a80e1a723d6d2f0a06542f01309ea632.jpg",
        "https://windowsforum.kr/files/attach/images/48/938/503/013/3c02c3f0c93684453691a479055fd1b2.jpg",
        "https://windowsforum.kr/files/attach/images/48/938/503/013/c1a33700a10b4c450f93a31907b1c6a5.jpg",
        "https://windowsforum.kr/files/attach/images/48/938/503/013/dafee7217ac0e4d045f12c25b089797b.jpg",
        "https://windowsforum.kr/files/attach/images/48/938/503/013/fda832f8e1bd9d8ee82afe8b4088f32f.jpg"
    )

    //저 해상도
//    var loadIv: String = "https://t1.daumcdn.net/cfile/tistory/25257E4753D84EE013"
//    var loadIv2: String = "https://t1.daumcdn.net/cfile/tistory/99531D415E170D2918"

    //고 해상도
    var loadIv: String = "https://windowsforum.kr/files/attach/images/48/938/503/013/681090ef0e0c9ad988309e39a19c9456.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        test()
    }

    fun test(){

        CoroutineScope(Dispatchers.IO).launch {

        }
        /**
        캐시 정책 DiskCacheStrategy
        DiskCacheStrategy.NONE: 아무 것도 캐시하지 않음을 나타냅니다
        DiskCacheStrategy.SOURCE: 원래 이미지만 캐시됨을 나타냅니다
        DiskCacheStrategy.RESULT: 변환된 이미지만 캐시합니다(기본 옵션)
        DiskCacheStrategy.ALL: 원래 그림도 캐시하고 변환된 그림도 캐시합니다
         */
        Glide.with(this)
            .load(loadIv)
            .placeholder(R.mipmap.ic_launcher)
            .into(binding.ivImage)

        for (item:String in ivArr){
            //다음 이미지
            Glide.with(this)
                .load(item)
//            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
                .preload()
        }

        binding.tvBtn.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }


}