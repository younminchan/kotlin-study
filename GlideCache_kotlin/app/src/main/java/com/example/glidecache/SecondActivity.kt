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


    // 1. ???????????? ?????? ?????? ?????????
    private fun default() {
        Glide.with(this).load(loadIv).into(binding.ivImage)
    }
    // 2. ???????????? ????????? ????????????
    // ?????? ?????? DiskCacheStrategy
    // DiskCacheStrategy.NONE: ?????? ?????? ???????????? ????????? ???????????????
    // DiskCacheStrategy.SOURCE: ?????? ???????????? ???????????? ???????????????
    // DiskCacheStrategy.RESULT: ????????? ???????????? ???????????????(?????? ??????)
    // DiskCacheStrategy.ALL: ?????? ????????? ???????????? ????????? ????????? ???????????????
    private fun diskCacheStrategy () {
        Glide.with(this).load(loadIv).diskCacheStrategy(DiskCacheStrategy.ALL).into(binding.ivImage)
    }
    // 3. ?????? ??????????????? ????????? ????????????
    //???????????? ????????? ?????? ?????? ????????? ???????????? ???????????? load ?????? ????????? load??? ???????????? ????????????.
    private fun onlyRetrieveFromCache(){
        Glide.with(this).load(loadIv).onlyRetrieveFromCache(true).into(binding.ivImage)
    }

    // 4. ??????????????? ???????????? skipMemoryCache()
    // ??????????????? ???????????? iskCacheStrategy(DiskCacheStrategy.NONE)
    // ?????? ??????????????? ?????? ?????????
    // ??????????????? ????????? ?????? ?????? ????????? ??????.
    // ???????????? ??????, ????????? ??? ???????????? ??? ???????????? ????????? ????????? ???????????? ???????????? load?????? ?????? ??????
    // ????????? ?????? ????????? ??????
    private fun skipMemoryCache(){
        Glide.with(this).load(loadIv)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.ivImage)
    }



}