package com.example.gyroscope_kotlin

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class GyroscopeListener {
    lateinit var mSensorManager: SensorManager
    lateinit var mGyroSensor: Sensor
    lateinit var mGyroListen: SensorEventListener

    fun startListener() {
        mSensorManager = App.activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        mGyroListen = mSensorEventListener

        mSensorManager.registerListener(mGyroListen, mGyroSensor, SensorManager.SENSOR_DELAY_NORMAL)
        /**
         * SENSOR_DELAY_FASTEST: 빈번하게
         * SENSOR_DELAY_GAME: 게임에 적합한 정도
         * SENSOR_DELAY_NOMAL: 화면 방향이 전환될 때 적합한 정도
         * SENSOR_DELAY_DELAY_UI: 사용자 인터페이스를 표시하기에 적합한 정도
         */

    }

    fun stopListener() {
        mSensorManager.unregisterListener(mGyroListen)
    }

    private var mSensorEventListener = object : SensorEventListener{
        /** 센서 정밀도가 변경되면 호출 */
        override fun onSensorChanged(sensorEvent: SensorEvent?) {
            /**
             * SensorEvent.values[0]: x축
             * SensorEvent.values[1]: y축
             * SensorEvent.values[2]: z축
             */
            if (sensorEvent != null) {
                var sensorResult = "(X축) ${sensorEvent.values[0]} / (Y축) ${sensorEvent.values[1]} / (Z축) ${sensorEvent.values[2]}"
                Log.e("YMC", sensorResult)
            }
        }

        /** 센서값이 변경되면 호출 */
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }
}