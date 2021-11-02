package com.example.room_recyclerview_kotlin

import androidx.lifecycle.LiveData

//앱에서 사용하는 데이터와 그 데이터 통신을 하는 역할
class UserRespository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }
}