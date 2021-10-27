package com.example.livedata_kotlin

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//TimerViewModel.kt
class TimerViewModel : ViewModel() {
    private lateinit var timer: CountDownTimer
    var timerValue = MutableLiveData<Long>()

    private val _seconds = MutableLiveData<Int>() //Observe
    var finished = MutableLiveData<Boolean>() //Observe

    fun seconds(): LiveData<Int>{
        return _seconds
    }

    fun startTimer(){
        timer = object: CountDownTimer(timerValue.value!!.toLong(), 1000){
            override fun onTick(millisUntilFinished: Long) {
                val timeLeft = millisUntilFinished/1000
                _seconds.value = timeLeft.toInt()
            }

            override fun onFinish() {
                finished.value = true
            }
        }.start()
    }

    fun stopTimer(){
        timer.cancel()
    }
}