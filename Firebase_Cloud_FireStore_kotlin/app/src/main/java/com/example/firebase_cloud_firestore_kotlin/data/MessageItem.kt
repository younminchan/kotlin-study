package com.example.firebase_cloud_firestore_kotlin.data

/**
 * [중요] Firestore의 ModelClass는 반드시 초기값이 설정 되어 있어야합니다.
 * */
data class MessageItem(
    val name: String = "",
    val message: String = "",
    val time: String = "",
    val createdAt: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now(),
)