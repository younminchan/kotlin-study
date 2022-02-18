package com.example.qrcode_kotlin

import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

//QRCodeScan.kt
class QRCodeScan(private val act: MainActivity) {

    /** QRCode Scan */
    fun startQRScan(){
        val intentIntegrator = IntentIntegrator(act)

        intentIntegrator.setPrompt("안내선 안에 QR코드를 맞추면 자동으로 인식됩니다.") //QR코드 스캔 액티비티 하단에 띄울 텍스트 설정
        intentIntegrator.setOrientationLocked(false)                       //화면회전을 막을 것인지 설정 (default 세로모드)
        intentIntegrator.setBeepEnabled(false)                             //QR코드 스캔 시 소리를 낼 지 설정
        activityResult.launch(intentIntegrator.createScanIntent())
    }

    /** onActivityResult */
    private val activityResult = act.registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        val data = result.data

        val intentResult: IntentResult? = IntentIntegrator.parseActivityResult(result.resultCode, data)
        if(intentResult != null){
            //QRCode Scan 성공
            if(intentResult.contents != null){
                //QRCode Scan result 있는경우
                Toast.makeText(act, "인식된 QR-data: ${intentResult.contents}", Toast.LENGTH_SHORT).show()
            }else{
                //QRCode Scan result 없는경우
                Toast.makeText(act, "인식된 QR-data가 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }else{
            //QRCode Scan 실패
            Toast.makeText(act, "QR스캔에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }

    }
}