package com.example.firebase_cloud_firestore_kotlin.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

object Utils {
    /** 토스트 메세지(중복방지) */
    var toast: Toast? = null
    fun toastMsg(context: Context, string: String) {
        try {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed(Runnable {
                toast?.cancel()
                toast = Toast.makeText(context, string, Toast.LENGTH_SHORT)
                toast?.show()
            }, 0)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}