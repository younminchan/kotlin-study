package com.example.firebase_cloud_firestore_kotlin.util

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesManager {
    private const val PREFS_NAME = "firestore"
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun putString(key: String, value: String) { sharedPreferences.edit().putString(key, value).apply() }
    fun getString(key: String, defaultValue: String): String { return sharedPreferences.getString(key, defaultValue) ?: defaultValue }

    fun putBoolean(key: String, value: Boolean) { sharedPreferences.edit().putBoolean(key, value).apply() }
    fun getBoolean(key: String, defaultValue: Boolean): Boolean { return sharedPreferences.getBoolean(key, defaultValue) }

    fun putInt(key: String, value: Int) { sharedPreferences.edit().putInt(key, value).apply() }
    fun getInt(key: String, defaultValue: Int): Int { return sharedPreferences.getInt(key, defaultValue) }
}