package com.example.livedata_kotlin

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.livedata_kotlin.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityMainBinding
    private lateinit var mModel: RandomNumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Get the view model
        mModel = ViewModelProviders.of(this).get(RandomNumberViewModel::class.java)

        //Create the observer which updates the ui
        val randomNumberObserver = Observer<Int> { newNumber ->
            //Update the ui wuth current data
            binding.mTextview.text = "Current Number : $newNumber"
        }

        //Observe the live data, passing in this activity as the life cycle owner and the observer
        mModel.currentRandomNUmber.observe(this, randomNumberObserver)

        //Button click listener
        binding.mButton.setOnClickListener {
            //Change the data
            mModel.currentRandomNUmber.value = Random().nextInt(50)
        }
    }
}