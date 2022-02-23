package com.example.dynamiclink_kotlin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dynamiclink_kotlin.databinding.ActivityMainBinding
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDynamicLinks()
//        var deepLinkStr = createDynamicLink()
//        Log.e("YMC", "DeepLinkStr: ${deepLinkStr}")
    }

    /** DynamicLink */
    fun initDynamicLinks() {
        /** 딥링크 수신*/
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener {
                var deppLink: Uri? = null
                if (it != null) {
                    /** 딥링크 데이터 수신성공 */
                    deppLink = it.link

                    //TODO: 단축URL: https://deeplink2022.page.link/1  ,  딥링크: https://shopping.naver.com/home/p/index.naver
                    Log.e("YMC", "deppLink: ${deppLink?.path}") //deepLink:    /home/p/index.naver

//                    val eventNo = deppLink?.path!!.replace("/data/", "")
//                    Log.e("YMC","eventNo: ${eventNo}")
                } else {
                    // handle
                }
            }
            .addOnFailureListener {
                // handle
            }
    }

    /** 긴 동적 링크만들기 - Firebase에서 직접만들면 이코드는 필요 없음! */
    private val DEEPLINK_URL = "https://deeplink2022.page.link/data/123" //딥 링크
    private val SHORT_DYNAMIC_LINK = "https://deeplink2022.page.link/1"  //짧은 동적 링크
    private val PACKAGE_NAME = "com.example.deeplink_receive"

    private fun createDynamicLink(): String {
        return FirebaseDynamicLinks.getInstance()
            .createDynamicLink()
            .setLink(Uri.parse(DEEPLINK_URL))
            .setDomainUriPrefix(SHORT_DYNAMIC_LINK)
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder(PACKAGE_NAME)
                    .build()
            )
            .buildDynamicLink()
            .uri.toString()
//            .uri.toString() + "event/1"  //  https://URL/event/3 의 형태로 만들기 위해 추가
    }
}