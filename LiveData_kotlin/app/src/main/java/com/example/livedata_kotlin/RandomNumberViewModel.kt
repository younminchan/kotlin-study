package com.example.livedata_kotlin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RandomNumberViewModel : ViewModel() {

    //Create a LiveData with a random number
    val currentRandomNUmber: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
}