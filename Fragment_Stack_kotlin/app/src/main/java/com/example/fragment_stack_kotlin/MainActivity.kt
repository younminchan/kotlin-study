package com.example.fragment_stack_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fragment_stack_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var isFragmentSub1: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFragment()
    }

    private fun initFragment() {
        var fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_main, MainFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        binding.bChangeFragment.setOnClickListener {
            var fragmentTransaction = supportFragmentManager.beginTransaction()
            if (isFragmentSub1) {
                fragmentTransaction.replace(R.id.fragment_main, Sub2Fragment())
                    .addToBackStack(null)
            } else {
                fragmentTransaction.replace(R.id.fragment_main, Sub1Fragment())
                    .addToBackStack(null)
            }
            fragmentTransaction.commit()

            isFragmentSub1 = !isFragmentSub1
        }
    }

    /**
     백 스택(BackStack) 추가 관련
     addToBackStack (null) -> 왜 null이라고 하는가?
     이름을 따로 지을것이 없으면 null로 그냥 이름을 집어 넣으라는 뜻
     */
    
}