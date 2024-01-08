package com.example.firebase_cloud_firestore_kotlin.data

data class FirebaseData(
    val sampleName: String,
    val sampleNumber: Int,
    val sampleBoolean: Boolean,
    val createdAt: com.google.firebase.Timestamp,
)