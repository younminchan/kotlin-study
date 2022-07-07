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
        fragmentTransaction.add(R.id.fragment_sub, Sub1Fragment())
        fragmentTransaction.commit()
        
        binding.bChangeFragment.setOnClickListener {
            var fragmentTransaction = supportFragmentManager.beginTransaction()
            if (isFragmentSub1) {
                fragmentTransaction.replace(R.id.fragment_sub, Sub2Fragment())
            } else {
                fragmentTransaction.replace(R.id.fragment_sub, Sub1Fragment())
            }
            fragmentTransaction.commit()

            isFragmentSub1 = !isFragmentSub1
        }
    }
}