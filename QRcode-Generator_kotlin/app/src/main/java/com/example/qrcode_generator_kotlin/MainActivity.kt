package com.example.qrcode_generator_kotlin

import android.Manifest
import android.os.Bundle
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.qrcode_generator_kotlin.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
//    lateinit var toast: Toast
    lateinit var qrCodeScan: QRCodeScan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        qrCodeScan = QRCodeScan(this)
        init()
    }

    private fun init(){
        /** QR-Code 생성 */
        binding.tvQrGenerate.setOnClickListener {
            //KeyBoard 숨기기
            val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etQrGenerate.windowToken, 0)

            if(binding.etQrGenerate.text.isNullOrEmpty()){
                showToastMsg("생성할 QRcode 데이터를 입력해주세요.")
            }else{
//                var ImageQRcode = generaterQRCode(binding.etQrGenerate.text.toString())
//                binding.ivQr.setImageBitmap(ImageQRcode)
            }
        }

        /** QR-Code 인식 */
        binding.tvQrScan.setOnClickListener {
            //권한 체크
//            qrCodeScan.startQRScan()

            scanQrCode()

//            val permission = Manifest.permission.CAMERA
//            if(Utils.existsPermission(permission)) {
//                scanQrCode()
//            } else {
//                permissionResult.launch(permission)
//            }
        }
    }

    /** QRCode 생성 */
//    fun generaterQRCode(contents: String) : Bitmap{
//        val writer = QRCodeWriter()
//        val bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, 512, 512)
//        val width = bitMatrix.width
//        val height = bitMatrix.height
//        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
//        for (x in 0 until width) {
//            for (y in 0 until height) {
//                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
//            }
//        }
//        return bitmap
//    }

    /** QRCode 스캔 */
    private fun scanQrCode() {
        val intentIntegrator = IntentIntegrator(this)

        intentIntegrator.setPrompt("안내선 안에 QR코드를 맞추면 자동으로 인식됩니다.") //QR코드 스캔 액티비티 하단에 띄울 텍스트
        intentIntegrator.setOrientationLocked(false) // default가 세로모드인데 / 화면회전을 막을 것인지
        intentIntegrator.setBeepEnabled(false)       //QR코드 스캔 시 소리를 낼 지 설정
//        intentIntegrator.initiateScan()
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
        if(intentResult != null) {
            // QR Code 값이 있는 경우
            if(intentResult.contents != null) {
                showToastMsg(intentResult.contents)
            }
            // QR Code 값이 없는 경우
            else {
                showToastMsg("QRcode값이 존재하지 않습니다.")
            }
        }
        // QR Code 실패
        else {
            showToastMsg("QRcode값을 불러올 수 없습니다.")
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

    private fun showToastMsg(msg: String){
//        if(toast != null){
//            toast.cancel()
//        }
//        toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT)
//        toast.setGravity(Gravity.CENTER, 0, 0)
//        toast.show()
    }
}