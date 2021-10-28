package com.example.databinding_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.databinding_kotlin.databinding.ActivityMainBinding

//MainActivity.kt
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var liveText = MutableLiveData<String>()
    var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            //(중요) DataBinding에 LifeCycleOwner를 지정해줘야 LiveData가 실시간적으로 변경됨
            lifecycleOwner = this@MainActivity
            //xml파일에서 선언한 activity (이름이 같아야함!)
            activity = this@MainActivity
        }
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)


        //liveText값이 변경되는지 그냥 확인하는 용도
        liveText.observe(this, Observer {
            Log.e("YMC", "observe: ${binding.mButton.text.toString()}")
        })


        //DataBinding 예제
        //xml파일에서 설정한 value값은 kotlin에서 MutableLiveData로 선언이 되있다! - 변경을 바로 확인하기 위해서!
        binding.mButton.setOnClickListener {
            liveText.value = "DataBinding으로 변경된 textview ${count++}"
//            binding.invalidateAll() //data가 변한 후 연결된 view들에게 변화를 알려주는 함수
        }
    }
}