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
    /** Firebase쪽 부분이 제대로 설정이 되었다는 전제
     * 단축URL: https://deeplink2022.page.link/1  ("~~~.page.link" -> firebase쪽에서 제공하는 url)
     * 딥링크: https://shopping.naver.com/home/p/index.naver  (앱내 딥링크가 존재하지 않을시 다른URL로 돌아가는 것을 확인하기 위해 naverShopping으로 임시 지정)
     *
     * (설명)
     * 단축URL을 앱 또는 브라우저에서 실행 시 -> Firebase쪽에서 수신을 통해 딥링크로 변경을 해줌
     * firebase쪽에서 Flow가 어떻게 돌아가는지 확인 할 수 있으며
     * 또 해당 딥링크로 변환했을시 앱에서 scheme를 "https", host를 "shopping.naver.com/home"으로 해당되는 딥링크를 수신처리
     * 만약 해당되는 앱이 없을 시 딥링크 URL를 그대로 띄우거나 구글, 앱스토어마켓으로 이동시킴! */
    fun initDynamicLinks() {
        /** 딥링크 수신*/
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener {
                var deppLink: Uri? = null
                if (it != null) {
                    /** 딥링크 데이터 수신성공 */
                    deppLink = it.link
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