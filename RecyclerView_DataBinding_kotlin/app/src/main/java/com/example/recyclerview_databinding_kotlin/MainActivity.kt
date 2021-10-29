package com.example.recyclerview_databinding_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview_databinding_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var talkAdapter: TalkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.apply {
            lifecycleOwner = this@MainActivity
            activityXml = this@MainActivity
        }
        setContentView(binding.root)


        talkAdapter = TalkAdapter()
        binding.mRvTalk.adapter = talkAdapter
        binding.mRvTalk.setHasFixedSize(false)
        binding.mRvTalk.layoutManager = LinearLayoutManager(this)


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.b_submit_talk -> {
                var s: String = binding.etTalk.text.toString()
                talkAdapter.addItem(s)
                binding.mRvTalk.smoothScrollToPosition(talkAdapter.list.size - 1)
                talkAdapter.notifyItemChanged(talkAdapter.list.size - 1)
            }
        }
    }
}