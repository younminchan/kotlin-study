package com.example.deeplink_receive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.deeplink_receive.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDeepLink()
    }

    /** DeepLink */
    private fun initDeepLink() {
        if (Intent.ACTION_VIEW.equals(intent.action)) {
            var uri = intent.data
            if (uri != null) {
                //TODO: "deeplink://host?data1=DeepLinkData1&data2=DeepLinkData2"
                var dl_data1 = uri.getQueryParameter("data1")
                var dl_data2 = uri.getQueryParameter("data2")

                binding.tvDeeplinkReceive.text = "딥링크 수신받은 값\n" +
                        "dl_data1: $dl_data1 \n" +
                        "dl_data2: $dl_data2"
            }
        }
    }
}