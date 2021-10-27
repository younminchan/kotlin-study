package com.example.livedata_kotlin

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.livedata_kotlin.databinding.ActivityMainBinding
import java.util.*

//MainActivity.kt
class MainActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityMainBinding
    private lateinit var mModel: RandomNumberViewModel
    private lateinit var mModel2: TimerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //TODO: 예제1
        //Get the view model
//        mModel = ViewModelProviders.of(this).get(RandomNumberViewModel::class.java) //방법1
        mModel = ViewModelProvider(this).get(RandomNumberViewModel::class.java) //방법2

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



        //TODO: 예제2
        //Get the view model
        mModel2 = ViewModelProvider(this).get(TimerViewModel::class.java)

        //Observe case1
        mModel2.seconds().observe(this, Observer {
            binding.mTextview2.text = it.toString()
        })

        //Observe case2
        mModel2.finished.observe(this, Observer {
            if(it){
                Toast.makeText(this, "Finished!", Toast.LENGTH_SHORT).show()
            }
        })

        binding.mTimeStart.setOnClickListener {
            if(binding.mInputText2.text.isEmpty() || binding.mInputText2.text.length>10){
                Toast.makeText(this, "Invaild Number!", Toast.LENGTH_SHORT).show()
            }else{
                mModel2.timerValue.value = binding.mInputText2.text.toString().toLong()
                mModel2.startTimer()
            }
        }
        binding.mTimeFinish.setOnClickListener {
            binding.mTextview2.text = "0"
            mModel2.stopTimer()
        }
    }
}