package com.example.glidecache

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.glidecache.databinding.ActivitySecondBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySecondBinding
//    var loadIv: String = "https://t1.daumcdn.net/cfile/tistory/25257E4753D84EE013"
    var loadIv: String = "https://windowsforum.kr/files/attach/images/48/938/503/013/a80e1a723d6d2f0a06542f01309ea632.jpg"
    var ivArr = listOf(
        "https://windowsforum.kr/files/attach/images/48/938/503/013/a80e1a723d6d2f0a06542f01309ea632.jpg",
        "https://windowsforum.kr/files/attach/images/48/938/503/013/3c02c3f0c93684453691a479055fd1b2.jpg",
        "https://windowsforum.kr/files/attach/images/48/938/503/013/c1a33700a10b4c450f93a31907b1c6a5.jpg",
        "https://windowsforum.kr/files/attach/images/48/938/503/013/dafee7217ac0e4d045f12c25b089797b.jpg",
        "https://windowsforum.kr/files/attach/images/48/938/503/013/fda832f8e1bd9d8ee82afe8b4088f32f.jpg"
    )
    var pos = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        test()
    }

    fun test(){

//        default()
        onlyRetrieveFromCache()

        binding.tvIvChange.setOnClickListener {
            if(pos > ivArr.size-1){
                pos = 0
            }

            Glide.with(this).load(ivArr[pos]).into(binding.ivImage)
            Glide.with(this).load(ivArr[pos]).onlyRetrieveFromCache(true).into(binding.ivImage)
            Glide.with(this).load(ivArr[pos]).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.ivImage)

            pos++
        }
        binding.tvBtn.setOnClickListener {
            finish()
        }
    }


    // 1. 글라이드 기본 형태 사용법
    private fun default() {
        Glide.with(this).load(loadIv).into(binding.ivImage)
    }
    // 2. 글라이드 디스크 캐시전략
    // 캐시 정책 DiskCacheStrategy
    // DiskCacheStrategy.NONE: 아무 것도 캐시하지 않음을 나타냅니다
    // DiskCacheStrategy.SOURCE: 원래 이미지만 캐시됨을 나타냅니다
    // DiskCacheStrategy.RESULT: 변환된 이미지만 캐시합니다(기본 옵션)
    // DiskCacheStrategy.ALL: 원래 그림도 캐시하고 변환된 그림도 캐시합니다
    private fun diskCacheStrategy () {
        Glide.with(this).load(loadIv).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.ivImage)
    }
    // 3. 오직 캐시에서만 이미지 불러오기
    //이미지가 메모리 캐시 또는 디스크 캐시에서 발견되면 load 되고 없다면 load가 실행되지 않습니다.
    private fun onlyRetrieveFromCache(){
        Glide.with(this).load(loadIv).onlyRetrieveFromCache(true).into(binding.ivImage)
    }

    // 4. 메모리캐시 건너띄기 skipMemoryCache()
    // 디스크캐시 건너띄기 iskCacheStrategy(DiskCacheStrategy.NONE)
    // 둘다 건너띄기는 같이 쓰면됨
    // 일반적으로 캐시를 건너 뛰지 않는걸 권장.
    // 이미지를 검색, 디코딩 및 변환하여 새 썸네일을 만드는 것보다 캐시에서 이미지를 load하는 것이 빠름
    // 아래는 둘다 건너띈 코드
    private fun skipMemoryCache(){
        Glide.with(this).load(loadIv)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.ivImage)
    }



}