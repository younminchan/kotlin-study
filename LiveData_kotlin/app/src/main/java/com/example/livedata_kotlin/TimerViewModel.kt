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

    //이렇게 구문을 사용하는 이유는
    //실제 _seconds를 직접접근하는것이 아니라 읽기만 가능한 val의 seconds를 접근하여 원본이 수정되지 않도록 하기 위해서!
    val seconds: LiveData<Int>
        get() = _seconds


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