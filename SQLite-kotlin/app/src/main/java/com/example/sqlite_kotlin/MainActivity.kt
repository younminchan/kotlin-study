package com.example.sqlite_kotlin

import android.app.Activity
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlite_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    /** Native-DB */
    lateinit var dbHelper: NativeDBHelper
    lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = NativeDBHelper(this, "sqlite.db", null, 1)
        database = dbHelper.writableDatabase

        init()
    }

    fun init() {
        var TestDB = DBTestHelper(database)
        binding.tvSelect.setOnClickListener {

            binding.tvSelectResult.text = ""

            for (item in TestDB.select()) {
                var t_before = binding.tvSelectResult.text
                binding.tvSelectResult.text = "$t_before $item\n"
            }
        }

        binding.tvInsert.setOnClickListener {
            var key = "${binding.etKey.text}"
            var value = "${binding.etValue.text}"
            binding.etKey.text.clear()
            binding.etValue.text.clear()
            TestDB.insert(key, value)
            Toast.makeText(this, "insert 성공", Toast.LENGTH_SHORT).show()
        }

        binding.tvDelete.setOnClickListener {
            TestDB.delete()
            Toast.makeText(this, "delete 성공", Toast.LENGTH_SHORT).show()
            binding.tvSelectResult.text = ""
        }
    }
}