package com.example.emotionapi_kotlin

data class Faces(
    var celebrity: Celebrity,
    var emotion: Emotion,
    var age: Age,
    var gender: Gender,
    var pose: Pose
)


data class Celebrity(
    var value: String,
    var confidence: Double
)

data class Emotion(
    var value: String,
    var confidence: Double
)

data class Age(
    var value: String,
    var confidence: Double
)

data class Gender(
    var value: String,
    var confidence: Double
)

data class Pose(
    var value: String,
    var confidence: Double
)