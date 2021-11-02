package com.example.room_recyclerview_kotlin

import android.app.Application
import android.service.autofill.UserData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//ViewModel은 DB에 직접 접근하지 않아야함. Repository에서 데이터 통신해야함
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRespository
    val readAllData: LiveData<List<User>>

    init {
        val userDao = UserDatabase.getDatabase(application)!!.userDao()
        repository = UserRespository(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User) {
        CoroutineScope(Dispatchers.Main).launch {
            repository.addUser(user)
        }
    }

    //ViewModel에 파라미터를 넘기기 위해서, 파라미터를 포함한 Factory 객체를 생성하기 위한 클래스
    class Factory(val application: Application) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewModel(application) as T
        }
    }
}