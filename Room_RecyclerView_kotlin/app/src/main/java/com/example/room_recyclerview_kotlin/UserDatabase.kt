package com.example.room_recyclerview_kotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        //@Volatile: 접근가능한 변수의 값을 cache를 통해 사용하지 않고 thread가 직접 main memory에 접근하기 위해 동기화
        @Volatile
        private var instance: UserDatabase? = null

        //싱글톤으로 생성(자주 생성 시 성능 안좋음), 이미 존재할 경우 생성하지 않고 바로 반환
        fun getDatabase(context: Context): UserDatabase? {
            if (instance == null) {
                synchronized(UserDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user_database"
                    ).build()
                }
            }
            return instance
        }
    }
}