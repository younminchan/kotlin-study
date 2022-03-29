package com.example.vibrate_kotlin

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vibrate_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVibrate()
    }

    private fun initVibrate() {
        var vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        /** 진동 - 1번 */
        binding.tvVibrateOneshot.setOnClickListener {
            // 1초 진동 울리기
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(1000);
            }
            Toast.makeText(applicationContext, "진동 1회", Toast.LENGTH_SHORT).show()
        }

        /** 진동 - 패턴 */
        binding.tvVibratePattern.setOnClickListener {
            // 1초 대기 -> 1초 진동 -> 1초 대기 -> 1초 진동
            val vibratePattern = longArrayOf(1000, 1000, 1000, 1000)
            // 반복 없음
            val repeat = -1

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(vibratePattern, repeat))
            } else {
                vibrator.vibrate(vibratePattern, repeat)
            }
            Toast.makeText(applicationContext, "진동 패턴", Toast.LENGTH_SHORT).show()
        }

        /** 진동 - 취소 */
        binding.tvVibrateCancel.setOnClickListener {
            vibrator.cancel()
            Toast.makeText(applicationContext, "진동 취소", Toast.LENGTH_SHORT).show()
        }
    }
}