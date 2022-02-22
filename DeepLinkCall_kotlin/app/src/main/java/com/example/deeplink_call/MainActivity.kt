package com.example.deeplink_call

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.deeplink_call.databinding.ActivityMainBinding
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** ViewBinding 설정 */
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** Click-Listener */
        binding.tvDeepLink.setOnClickListener {
            try { 
                var url = "deeplink://host?data1=DeepLinkData1&data2=DeepLinkData2"
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK //새로운 창
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                /** 딥링크 호출 오류(해당 앱이 존재하지 않을 경우)*/
                Toast.makeText(this, "DeepLink 호출이 불가능 합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}