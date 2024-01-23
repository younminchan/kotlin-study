package com.example.qrcode_scan_generate_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.qrcode_scan_generate_kotlin.databinding.ActivityScanBinding
import com.journeyapps.barcodescanner.CaptureManager

/** Custom Scanner UI */
class ScanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    private lateinit var capture: CaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initScan(savedInstanceState)
    }

    /** init 설정 */
    private fun initScan(savedInstanceState: Bundle?) {
        // CaptureManager에 context와 xml에서 적용한 레이아웃을 설정
        capture = CaptureManager(this, binding.decoratedBarcodeView)
        // intent와 savedInstanceState를 설정
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.decode() //decode
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }
    override fun onPause() {
        super.onPause()
        capture.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }
}