package com.example.gyroscope_kotlin

import android.content.Context
import android.hardware.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.gyroscope_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding

    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
        /**
         * SENSOR_DELAY_FASTEST: 가능한 자주
         * SENSOR_DELAY_GAME: 게임에 적합한 정도
         * SENSOR_DELAY_NOMAL: 화면 방향이 전환될 때 적합한 정도
         * SENSOR_DELAY_DELAY_UI: 사용자 인터페이스를 표시하기에 적합한 정도
         */
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    //센서 정밀도가 변경되면 호출
    override fun onSensorChanged(sensorEvent: SensorEvent?) {
        /**
         * SensorEvent.values[0]: x축
         * SensorEvent.values[1]: y축
         * SensorEvent.values[2]: z축
         */
        if (sensorEvent != null) {
            Log.e("YMC", "(X축) ${sensorEvent.values[0]} / (Y축) ${sensorEvent.values[1]} / (Z축) ${sensorEvent.values[2]}")
        }
    }

    //센서값이 변경되면 호출
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}