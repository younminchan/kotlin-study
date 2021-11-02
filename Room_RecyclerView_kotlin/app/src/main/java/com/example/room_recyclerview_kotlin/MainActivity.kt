package com.example.room_recyclerview_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room_recyclerview_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        userViewModel = ViewModelProvider(this, UserViewModel.Factory(application)).get(UserViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adaper = MyAdapter()
        binding.recyclerView.adapter = adaper

        userViewModel.readAllData.observe(this, Observer {
            adaper.setData(it)
        })


        binding.tvAddUser.setOnClickListener {
            var name: String = binding.etName.text.toString()
            var age: Int = Integer.parseInt(binding.etAge.text.toString())
            val user = User(0, name, age)
            userViewModel.addUser(user)

            binding.etName.setText("")
            binding.etAge.setText("")
        }
    }
}