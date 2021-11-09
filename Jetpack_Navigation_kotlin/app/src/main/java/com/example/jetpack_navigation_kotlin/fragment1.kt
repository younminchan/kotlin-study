package com.example.jetpack_navigation_kotlin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.jetpack_navigation_kotlin.databinding.Fragment1FragmentBinding

class fragment1 : Fragment() {

    private var _binding: Fragment1FragmentBinding? = null
    private val binding get() = _binding!!

//    companion object {
//        fun newInstance() = fragment1()
//    }

    private lateinit var viewModel: Fragment1ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = Fragment1FragmentBinding.inflate(inflater, container, false)

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("return")?.observe(viewLifecycleOwner){
            binding.tv1.text = "fragment1에서 받은 데이터 ${it}"
        }

        binding.tvMovefragment1.setOnClickListener {
            //fragment2로 보낼 데이터 set
            val bundle = Bundle()
            bundle.putString("data", binding.et1.text.toString())

            //fragment2로 이동
            this.findNavController().navigate(R.id.fragment2, bundle)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //(테스트) Fragment - ViewModel 생성
        viewModel = ViewModelProvider(this).get(Fragment1ViewModel::class.java)
    }
}