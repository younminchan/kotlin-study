package com.example.viewpager2_tablayout_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.viewpager2_tablayout_kotlin.databinding.FragmentTab3Binding

// Tab3Fragment.kt
class Tab3Fragment : Fragment() {
    private var _binding: FragmentTab3Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        _binding = FragmentTab3Binding.inflate(inflater, container, false)
        return binding.root
    }
}