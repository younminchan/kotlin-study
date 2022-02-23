package com.example.deeplink_receive

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.deeplink_receive.databinding.ActivityMainBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDeepLink()

        initDynamicLinks()
//        var deepLinkStr = createDynamicLink()
//        Log.e("YMC", "DeepLinkStr: ${deepLinkStr}")
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


    /** 긴 동적 링크만들기 - Firebase에서 직접만들면 이코드는 필요 없음! */
    private val DEEPLINK_URL = "https://deeplink2022.page.link/data/123" //딥 링크
    private val SHORT_DYNAMIC_LINK = "https://deeplink2022.page.link/1" //짧은 동적 링크
    private val PACKAGE_NAME = "com.example.deeplink_receive"

    private fun createDynamicLink(): String {
        return FirebaseDynamicLinks.getInstance()
            .createDynamicLink()
            .setLink(Uri.parse(DEEPLINK_URL))
            .setDomainUriPrefix(SHORT_DYNAMIC_LINK )
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder(PACKAGE_NAME)
                    .build()
            )
            .buildDynamicLink()
            .uri.toString() + "event/1"  //  https://URL/event/3 의 형태로 만들기 위해 추가
    }

    fun initDynamicLinks(){
        /** 딥링크 수신*/
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener {
                var deppLink: Uri? = null
                if (it != null) {
                    /** 딥링크 데이터 수신성공 */
                    deppLink = it.link

                    //TODO: 단축URL: https://deeplink2022.page.link/1  ,  딥링크: https://shopping.naver.com/home/p/index.naver
                    Log.e("YMC","deppLink: ${deppLink?.path}") //deepLink:    /home/p/index.naver

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


}