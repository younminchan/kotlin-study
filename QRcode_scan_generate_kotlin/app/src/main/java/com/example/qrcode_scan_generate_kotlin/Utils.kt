package com.example.qrcode_scan_generate_kotlin

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object Utils {

    /** 권한 승인 여부 판별 */
    fun existsPermission(permissionName: String): Boolean
            = (ContextCompat.checkSelfPermission(App.INSTANCE, permissionName)
            == PackageManager.PERMISSION_GRANTED)


}