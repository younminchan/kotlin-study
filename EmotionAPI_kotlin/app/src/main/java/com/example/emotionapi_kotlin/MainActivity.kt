package com.example.emotionapi_kotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.emotionapi_kotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.Default).launch {
        }


//        binding.tvImage.setOnClickListener {
//            var intent = Intent()
//            intent.type = "image/*"
//            intent.action = Intent.ACTION_GET_CONTENT
//            startActivityForResult(intent, 0)
//        }

        startEmotionAPI()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                startEmotionAPI()
            }
        }
    }

    private fun startEmotionAPI() {
        val clientId = resources.getString(R.string.clientId)
        val clientSecret = resources.getString(R.string.clientSecret)

        val client = Retrofit.Builder()
            .baseUrl("https://openapi.naver.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = client.create(NaverApiInterface::class.java)

        var imgFile = File("data:image/jpeg;base64,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")
        val reBody: RequestBody = RequestBody.create(MediaType.parse("image/jpeg"), imgFile)

        val body = MultipartBody.Part.createFormData("image", imgFile.getName(), reBody)

        val call: Call<NaverRepo> = service.naverRepo(clientId, clientSecret, body)
        call.enqueue(object : Callback<NaverRepo>{
            override fun onResponse(call: Call<NaverRepo>, response: Response<NaverRepo>) {
                if(response.isSuccessful){
                    Log.e("YMC", "data: ${response.body()}")
                }else{
                    Log.e("YMC", "data else")
                }
            }

            override fun onFailure(call: Call<NaverRepo>, t: Throwable) {
                t.printStackTrace()
                Log.e("YMC", "실패")
            }

        })
    }
}