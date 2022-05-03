package com.example.signaturepad_kotlin

import android.Manifest
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.marginRight
import com.example.signaturepad_kotlin.databinding.ActivityMainBinding
import com.kyanogen.signatureview.SignatureView
import java.io.File
import java.io.FileOutputStream


/** MainActivity.kt*/
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //화면모드 설정 (세로-SCREEN_ORIENTATION_PORTRAIT, 가로-SCREEN_ORIENTATION_LANDSCAPE)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        /** SignPad layout 생성*/
        initSignaturePad()
    }

    /** 화면 크기 정보 가져오기 */
    fun getScreenSize(): Map<String, Int> {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        return mapOf("width" to width, "height" to height)
    }

    /** 코드로 레이아웃 생성 */
    private fun initSignaturePad(){
        //전체 Layout
        var m_llayout = LinearLayout(this)
        m_llayout.apply {
            orientation = LinearLayout.VERTICAL
            id = ViewCompat.generateViewId()
            gravity = Gravity.CENTER
            var params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            params.weight = 1F
            layoutParams = params
        }

        //싸인패드
        var w_ratio = 0.9
        var h_ratio = 0.7
        var m_signPad = SignatureView(this, null)
        m_signPad.apply {
            penColor = Color.parseColor("#000000")
            layoutParams = LinearLayout.LayoutParams(
                (getScreenSize().getValue("width") * w_ratio).toInt(),
                (getScreenSize().getValue("height") * h_ratio).toInt()
            )
            id = ViewCompat.generateViewId()
        }

        //하단 버튼
        var m_Bottom = LinearLayout(this)
        m_Bottom.apply {
            orientation = LinearLayout.HORIZONTAL
            id = ViewCompat.generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                (getScreenSize().getValue("width") * w_ratio).toInt(),
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        var b_clear = Button(this).apply {
            id = ViewCompat.generateViewId()
            var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.weight = 1F
            layoutParams = params
            gravity = Gravity.CENTER
            setBackgroundColor(Color.parseColor("#F0F0F0"))
            text = "Clear"
            setOnClickListener {
                m_signPad.clearCanvas()
            }
        }

        var b_save = Button(this).apply {
            var params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            id = ViewCompat.generateViewId()
            params.weight = 1F
            layoutParams = params
            gravity = Gravity.CENTER
            setBackgroundColor(Color.parseColor("#F0F0F0"))
            text = "Save"
            setOnClickListener {
                Log.e("YMC", "b_save click")

                if(!m_signPad.isBitmapEmpty){
                    /** 권한 체크 */
                    if(!checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE) || !checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        return@setOnClickListener
                    }

                    /** 그림 저장 */
                    if(!imageExternalSave(m_signPad.signatureBitmap, getString(R.string.app_name))){
                        Toast.makeText(this@MainActivity, "사인패드 저장에 실패하였습니다", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@MainActivity, "사인패드를 갤러리에 저장하였습니다.", Toast.LENGTH_SHORT).show()
                        binding.clMain.removeAllViews()
                    }
                }else {
                    Toast.makeText(this@MainActivity, "사인패드가 비어있습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
        m_Bottom.addView(b_clear)
        m_Bottom.addView(b_save)
        m_llayout.addView(m_signPad)
        m_llayout.addView(m_Bottom)
        binding.clMain.addView(m_llayout)
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

                //갤러리 갱신4
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