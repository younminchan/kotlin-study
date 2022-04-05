package com.example.signaturepad_kotlin

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.signaturepad_kotlin.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //화면모드 설정 (세로-SCREEN_ORIENTATION_PORTRAIT, 가로-SCREEN_ORIENTATION_LANDSCAPE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initSignaturePad()
    }

    private fun initSignaturePad(){
        /** 초기화 */
        binding.bClear.setOnClickListener {
            binding.signaturePad.clearCanvas()
        }

        /** 저장 */
        binding.bSave.setOnClickListener {
            if(!binding.signaturePad.isBitmapEmpty){
                /** 권한 체크 */
                if(!checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || !checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    return@setOnClickListener
                }

                /** 그림 저장 */
                if(!imageExternalSave(binding.signaturePad.signatureBitmap, this.getString(R.string.app_name))){
                    Toast.makeText(this, "사인패드 저장에 실패하였습니다", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "사인패드를 갤러리에 저장하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }else {
                Toast.makeText(this, "사인패드가 비어있습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        /**
         * 관련자료
            1. [Android Studio] Bitmap을 File로 변환하기: https://crazykim2.tistory.com/445 [차근차근 개발일기+일상]
            2. [Android Studio] 비트맵 사진을 갤러리에 저장하기 코틀린 Bitmap To Gallery Kotlin: https://devsmin.tistory.com/m/27
         */
    }

    /** 이미지 저장 */
    private fun imageExternalSave(bitmap: Bitmap, path: String): Boolean {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state) {

            val rootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
            val dirName = "/" + path
            val fileName = System.currentTimeMillis().toString() + ".png"
            val savePath = File(rootPath + dirName)
            savePath.mkdirs()

            val file = File(savePath, fileName)
            if (file.exists()) file.delete()

            try {
                val out = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()

                //갤러리 갱신
                galleyAddPic(file)
                return true
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }

    /** 갤러리 갱신 */
    private fun galleyAddPic(file: File){
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            mediaScanIntent.data = Uri.fromFile(file)
            sendBroadcast(mediaScanIntent)
            Log.e("YMC", "currentPhotoPath2 : $file")
        }
    }

    /** 권한 체크 */
    private fun checkPermission(permission: String): Boolean {
        val permissionChecker = ContextCompat.checkSelfPermission(applicationContext, permission)

        //권한이 없으면 권한 요청
        if (permissionChecker == PackageManager.PERMISSION_GRANTED){
            return true
        }

        ActivityCompat.requestPermissions(this, arrayOf(permission), 100)
        return false
    }
}