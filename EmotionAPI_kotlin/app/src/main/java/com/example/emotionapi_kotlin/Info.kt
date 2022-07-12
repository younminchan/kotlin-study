package com.example.emotionapi_kotlin


data class Info(
    var faceCount: Int,
    var size: Size
)
data class Size(
    var width: Int,
    var height: Int,
)
