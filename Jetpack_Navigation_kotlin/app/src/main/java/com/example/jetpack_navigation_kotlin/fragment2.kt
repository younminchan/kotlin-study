package com.example.jetpack_navigation_kotlin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.jetpack_navigation_kotlin.databinding.FragmentFragment2Binding


class fragment2 : Fragment() {
    private var _binding: FragmentFragment2Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFragment2Binding.inflate(inflater, container, false)

        //fragment1에서 받은 데이터 출력
        binding.tv2.text = arguments?.getString("data")

        //뒤로가기 TextView 클릭 시
        binding.tvMovefragment2.setOnClickListener {
            goBack()
        }

        //뒤로가기 버튼 눌렀을때
        requireActivity().onBackPressedDispatcher.addCallback{
            goBack()
        }

        return binding.root
    }

    private fun goBack(){
        //fragment1으로 보낼 데이터 set
        val returnMsg = binding.et2.text.toString()
        findNavController().apply {
            previousBackStackEntry?.savedStateHandle?.set("return", returnMsg)
            popBackStack()
        }

//        findNavController().previousBackStackEntry?.savedStateHandle?.set("return", returnMsg)
//        findNavController().popBackStack()
    }
}