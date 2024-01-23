package com.example.qrcode_scan_generate_kotlin

import android.Manifest
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.qrcode_scan_generate_kotlin.databinding.ActivityMainBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.journeyapps.barcodescanner.BarcodeEncoder

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)

        /** QR-Code 생성 */
        binding.tvQrGenerate.setOnClickListener {
            val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etQrGenerate.windowToken, 0) //KeyBoard 숨기기

            if (binding.etQrGenerate.text.isNullOrEmpty()) {
                showToastMsg("생성할 QRcode 데이터를 입력해주세요.")
            } else {
                var ImageQRcode = generaterQRCode(binding.etQrGenerate.text.toString())
                binding.ivQr.setImageBitmap(ImageQRcode)
            }
        }

        /** QR-Code 인식 */
        binding.tvQrScan.setOnClickListener {
            //권한 체크
            val permission = Manifest.permission.CAMERA
            if (Utils.existsPermission(permission)) {
                scanQrCode()
            } else {
                permissionResult.launch(permission)
            }
        }
    }

    /** TODO: QRCode 생성 */
    fun generaterQRCode(contents: String) : Bitmap {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(contents, BarcodeFormat.QR_CODE, 512, 512)
        return bitmap
    }

    /** TODO: QRCode 스캔 */
    private fun scanQrCode() {
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setPrompt("안내선 안에 QR코드를 맞추면 자동으로 인식됩니다.") // QR코드 스캔 액티비티 하단에 띄울 텍스트
        intentIntegrator.setOrientationLocked(false)                       // default가 세로모드인데 / 화면회전을 막을 것인지
        intentIntegrator.setBeepEnabled(false)                             // QR코드 스캔 시 소리를 낼 지 설정
        intentIntegrator.captureActivity = ScanActivity::class.java        // Scanner가 있는 Custom_activity 적용
        activityResult.launch(intentIntegrator.createScanIntent())
    }

    /** onActivityResult */
    //rememberLauncherForActivityResult
    //registerForActivityResult
    private val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val resultCode = result.resultCode
        val data = result.data
        val intentResult: IntentResult? = IntentIntegrator.parseActivityResult(resultCode, data)

        // QR Code 성공
        if (intentResult != null) {
            // QR Code 값이 있는 경우
            if (intentResult.contents != null) {
                showToastMsg("인식된 QR-data: ${intentResult.contents}")
            }
            // QR Code 값이 없는 경우
            else {
                showToastMsg("인식된 QR-data가 없습니다.")
            }
        }
        // QR Code 실패
        else {
            showToastMsg("QR스캔에 실패했습니다.")
        }
    }

    /** onRequestPermissionResult */
    private val permissionResult = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            scanQrCode()
        } else {
            showToastMsg("해당 권한이 거부되었습니다.")
        }
    }

    /** Toast Message */
    private fun showToastMsg(msg: String) {
        toast.cancel()
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}